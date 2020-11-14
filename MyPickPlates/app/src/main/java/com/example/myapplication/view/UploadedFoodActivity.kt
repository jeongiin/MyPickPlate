package com.example.myapplication.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.utils.Photo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_uploaded_food.*
import java.lang.reflect.Type


class UploadedFoodActivity : AppCompatActivity() {
    private val CHOOSE_IMAGE = 1001
    private var labelidx = 0
    private lateinit var imageUri : String
    private lateinit var photoImage: Bitmap
    private lateinit var labelList: ArrayList<String>

    // SAVE 버튼 --> 클릭 시 음식 사진, 음식 사진 id, 음식 이름을 배열에 저장 --> 객체 생성 해야 할듯
    // editText 버튼 --> 추측 된 이름이 마음에 들지 않을 경우 작명 가능

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uploaded_food)
        photoImage = intent.getParcelableExtra("image")
        Log.d("이미지", photoImage.toString())
        labelList = intent.getStringArrayListExtra("label")
        Log.d("트라이", labelList.toString())
        imageUri = intent.getStringExtra("uri")
        Log.d("이미지in업로디드", imageUri)

        if (intent.hasExtra("image") || intent.hasExtra("label") || intent.hasExtra("uri")) {
            iv_food.setImageBitmap(photoImage)
            tv_food_name.text = labelList[labelidx]
        } else {
            Toast.makeText(this, "Image Error!", Toast.LENGTH_SHORT).show()
        }

        // Intent uploaded food activity
        var intent = Intent(this, RecommendFoodActivity::class.java)


       // Rename Food Photo
        btn_rename.setOnClickListener{
            labelidx ++
            if (labelidx < labelList.size)
                tv_food_name.text = labelList[labelidx]
            else
                labelidx = 0
                tv_food_name.text = labelList[labelidx]
            intent.putExtra("food_name",labelList[labelidx])
        }


       // Save Image
       // Use Shared Preferences : string array
       // key(img uri) + food name
       btn_save.setOnClickListener{
           // Saves image URI as string to Default Shared Preferences
           var photos = ReadPhotosData()!!
           Log.d("이미지 sp", photos.toString())
           for (photo in ReadPhotosData()) {
               Log.d("이미지데이터",photo?.uri + " : " + photo?.food_id + "\n") // 잘 받아와 진당 ㅠㅠㅠㅠㅠㅠㅠ
           }
           photos.add(Photo(imageUri, labelList[labelidx]))
           SavePhotoData(photos)

           this@UploadedFoodActivity.finish()
       }


        // Intent RecommendFoodActivity
        btn_search.setOnClickListener{
            startActivity(intent)
        }


    }


    private fun SavePhotoData(Photos: ArrayList<Photo?>?) {
        val preferences: SharedPreferences = getSharedPreferences("PHOTO_LIST", Context.MODE_PRIVATE)
        val editor = preferences!!.edit()
        val gson = Gson()
        val json = gson.toJson(Photos)
        editor.putString("PHOTO_LIST", json) // json 타입으로 변환한 객체 저장
        editor.commit() // 완료
    }


    private fun ReadPhotosData(): ArrayList<Photo?> {
        val sharedPrefs: SharedPreferences = getSharedPreferences("PHOTO_LIST", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPrefs.getString("PHOTO_LIST", "EMPTY")
        val type: Type = object : TypeToken<ArrayList<Photo?>?>() {}.type
        if (json.toString() != "EMPTY")
            return gson.fromJson(json, type) //Array List 반환

        var photos = ArrayList<Photo?>()
        return photos
    }


    override fun onBackPressed() {
        super.onBackPressed()
        this@UploadedFoodActivity.finish()
    }
}