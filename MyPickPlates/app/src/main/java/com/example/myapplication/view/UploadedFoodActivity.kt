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
    private lateinit var foodLabel: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uploaded_food)
        photoImage = intent.getParcelableExtra("image")
        foodLabel = intent.getStringExtra("label")

        if (intent.hasExtra("image") || intent.hasExtra("label")) {
            iv_food.setImageBitmap(photoImage)
            tv_food_name.text = foodLabel
        } else {
            Toast.makeText(this, "Image Error!", Toast.LENGTH_SHORT).show()
        }


    }
}