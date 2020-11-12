package com.example.myapplication.view

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.utils.ImageClassifier
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_uploaded_food.*


class UploadedFoodActivity : AppCompatActivity() {
    private val CHOOSE_IMAGE = 1001
    private lateinit var photoImage: Bitmap
    private lateinit var classifier: ImageClassifier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uploaded_food)
        classifier = ImageClassifier(getAssets()) //이미지 분류기
        photoImage = intent.getParcelableExtra("image")

        if (intent.hasExtra("image")) {
            iv_food.setImageBitmap(photoImage)
        }
        else { Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show() }


        classifier.recognizeImage(photoImage).subscribeBy(
            onSuccess = {
                tv_food_name.text = it.toString()
            }
        )
        Log.d("트라이", "classifier")
    }
}