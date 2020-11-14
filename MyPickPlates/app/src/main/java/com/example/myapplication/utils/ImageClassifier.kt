package com.example.myapplication.utils
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.util.Log
import com.example.myapplication.model.Result
import com.example.myapplication.utils.SetKey.DIM_BATCH_SIZE
import com.example.myapplication.utils.SetKey.DIM_IMG_SIZE_X
import com.example.myapplication.utils.SetKey.DIM_IMG_SIZE_Y
import com.example.myapplication.utils.SetKey.DIM_PIXEL_SIZE
import com.example.myapplication.utils.SetKey.IMAGE_MEAN
import com.example.myapplication.utils.SetKey.IMAGE_STD
import com.example.myapplication.utils.SetKey.INPUT_SIZE
import com.example.myapplication.utils.SetKey.LABEL_PATH
import com.example.myapplication.utils.SetKey.MAX_RESULTS
import com.example.myapplication.utils.SetKey.MODEL_PATH
import io.reactivex.Single
import org.tensorflow.lite.Interpreter
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.*

class ImageClassifier constructor(private val assetManager: AssetManager) {

    // 이미지를 인식하기 위한 분류자 클래스가 필요함.
    // 일반적으로 Interpreter 객체를 생성하고 interpreter.run()을 실행하는 것처럼 간단할 수 있지만
    // 이미지를 처리하기 때문에 이미지 구문 분석하고 크기를 조정해야 함
    // https://medium.com/@teresa.wu/tensorflow-image-recognition-on-android-with-kotlin-cee8d977ae9
    private var interpreter: Interpreter? = null
    private var labelProb: Array<FloatArray>
    private val labels = Vector<String>()
    private val intValues by lazy { IntArray(INPUT_SIZE * INPUT_SIZE) }
    private var imgData: ByteBuffer

    init {
        try {
            val br = BufferedReader(InputStreamReader(assetManager.open(LABEL_PATH)))
            while (true) {
                val line = br.readLine() ?: break
                labels.add(line)
            }
            br.close()
        } catch (e: IOException) {
            throw RuntimeException("Problem reading label file!", e)
        }
        labelProb = Array(1) { FloatArray(labels.size) } //ByteArray
        imgData = ByteBuffer.allocateDirect(4 * DIM_BATCH_SIZE * DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y * DIM_PIXEL_SIZE) //float 위해 수정
        imgData.order(ByteOrder.nativeOrder())
        try {
            interpreter = Interpreter(loadModelFile(assetManager, MODEL_PATH))
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }




    private fun convertBitmapToByteBuffer(bitmap: Bitmap) {
        imgData.rewind()
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        var pixel = 0
        for (i in 0 until DIM_IMG_SIZE_X) {
            for (j in 0 until DIM_IMG_SIZE_Y) {
                val value = intValues[pixel++]
                imgData.putFloat(((value shr 16 and 0xFF)- IMAGE_MEAN )/ IMAGE_STD)
                imgData.putFloat(((value shr 8 and 0xFF) - IMAGE_MEAN)/ IMAGE_STD)
                imgData.putFloat(((value and 0xFF)- IMAGE_MEAN)/ IMAGE_STD)
}
}
}

private fun loadModelFile(assets: AssetManager, modelFilename: String): MappedByteBuffer {
    val fileDescriptor = assets.openFd(modelFilename)
    val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
    val fileChannel = inputStream.channel
    val startOffset = fileDescriptor.startOffset
    val declaredLength = fileDescriptor.declaredLength
    return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
}

    fun recognizeImage(bitmap: Bitmap): Single<List<Result>> {
        return Single.just(bitmap).flatMap {
            convertBitmapToByteBuffer(it)
            Log.d("트라이","convertBitmapToByteBuffer 성공적")
            interpreter?.run(imgData, labelProb)
            Log.d("트라이","interpreter run 성공적") // --> run이 안되는 에러 해결
            val pq = PriorityQueue<Result>(3,
                Comparator<Result> { lhs, rhs ->
                    // Intentionally reversed to put high confidence at the head of the queue.
                    java.lang.Float.compare(rhs.confidence!!, lhs.confidence!!)
                })

            for (i in labels.indices) {
                pq.add(Result("" + i, if (labels.size > i) labels[i] else "unknown", labelProb[0][i].toFloat(), null))
            }


            val recognitions = ArrayList<Result>()


            val recognitionsSize = Math.min(pq.size, MAX_RESULTS)


            for (i in 0 until recognitionsSize) recognitions.add(pq.poll())

            return@flatMap Single.just(recognitions)
        }
    }

    fun close() {
        interpreter?.close()
    }
}
