package com.example.myapplication.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.utils.ImageClassifier
import com.example.myapplication.utils.SetKey
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_upload_food.*
import java.io.FileNotFoundException
import com.example.myapplication.model.Result

class UploadFoodActivity : AppCompatActivity() {
    private val CHOOSE_IMAGE = 1001
    private val labelList = ArrayList<String>()
    private lateinit var photoImage: Bitmap
    private lateinit var classifier: ImageClassifier
    private lateinit var label : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_food)
        classifier = ImageClassifier(getAssets()) //이미지 분류기
        checkPermission()
        iv_none.setOnClickListener {
            choosePicture()
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }
    }

    private fun choosePicture() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.addCategory(Intent.CATEGORY_OPENABLE) // 갤러리 입장
        startActivityForResult(intent, CHOOSE_IMAGE) // 이미지 선택하여 전달됨
    }

    /*
    Bitmap.createScaleBitemap 은 매우 중요한 단계
    이를 거치지 않을 경우 .ArrayOutOfIndexException 이 발생할 수 있음
    샘플 이미지가 모델 이미지와 같은 크기여야 하기 때문
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CHOOSE_IMAGE && resultCode == Activity.RESULT_OK)
            try {

                val stream = contentResolver!!.openInputStream(data!!.getData()!!)
                if (::photoImage.isInitialized) photoImage.recycle()
                photoImage = BitmapFactory.decodeStream(stream)
                photoImage = Bitmap.createScaledBitmap(photoImage,
                    SetKey.INPUT_SIZE,
                    SetKey.INPUT_SIZE, false)


                classifier.recognizeImage(photoImage).subscribeBy(
                    onSuccess = {
                        for( i in 0 until it.size)
                            labelList.add(i,it[i].toString())
                    }
                )
                Log.d("트라이", "classifier")


                // Intent uploaded food activity
                var intent = Intent(this, UploadedFoodActivity::class.java)
                intent.putExtra("image",photoImage)
                intent.putStringArrayListExtra("label",labelList)
                startActivity(intent)

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 0) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                imageResult.setEnabled(true)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        classifier.close()
    }
}