package com.example.myapplication.view

import android.Manifest
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapters.ViewFoodAdapter
import com.example.myapplication.utils.BitmapItem
import com.example.myapplication.utils.Photo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_view_food.*
import java.lang.reflect.Type


class ViewFoodActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_food)
        checkPermission()

        var photoData = ReadPhotosData()!!
        var bitmapData = ArrayList<BitmapItem>()

        Log.d("이미지 sp in Uploaded", photoData.toString())
        for (photo in ReadPhotosData()) {
            Log.d("이미지데이터 in Uploaded", photo?.uri + " : " + photo?.food_id + "\n")
        }

//        for (i in 0 until photoData.size){
//            Log.d("이미지URI 변형전", photoData[i].uri)
//            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, photoData[i].uri.toUri())
//
////            var uri = getRealPathFromURI(photoData[i].uri.toUri())
////            Log.d("이미지URI IN VIEW", uri)
//            bitmapData[i].bm = bitmap
//            bitmapData[i].id = photoData[i].food_id
//        }
        viewManager = LinearLayoutManager(this)
        viewAdapter = ViewFoodAdapter(photoData)

        recyclerView = findViewById<RecyclerView>(R.id.rv_food).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        //Intent
        var intentToUploadAct = Intent(this, UploadFoodActivity::class.java)
        var intentToUploadedAvt = Intent(this, UploadedFoodActivity::class.java)

        btn_add.setOnClickListener{
            startActivity(intentToUploadAct)
        }

        // btn_각각의 아이템 . 온클릭리스너 --> 이미지, 이미지이름(푸드네임), 저장하면 안되므로 Uri값X "CANT"로 각ㅁ
        // 업로디드에서 Uri 가 CANT로 갈 경우 TOAST로 이미 존재하는 이미지임을 표시


    }

    private fun getRealPathFromURI(contentURI: Uri): String? {
        val result: String
        val cursor: Cursor? = contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path!!
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }


    private fun SavePhotoData(Photos: ArrayList<Photo?>?) {
        val preferences: SharedPreferences = getSharedPreferences("PHOTO_LIST", Context.MODE_PRIVATE)
        val editor = preferences!!.edit()
        val gson = Gson()
        val json = gson.toJson(Photos)
        editor.putString("PHOTO_LIST", json) // json 타입으로 변환한 객체 저장
        editor.commit() // 완료
    }


    private fun ReadPhotosData(): ArrayList<Photo> {
        val sharedPrefs: SharedPreferences = getSharedPreferences("PHOTO_LIST", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPrefs.getString("PHOTO_LIST", "EMPTY")
        val type: Type = object : TypeToken<ArrayList<Photo?>?>() {}.type
        if (json.toString() != "EMPTY")
            return gson.fromJson(json, type) //Array List 반환

        var photos = ArrayList<Photo>()
        return photos
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE), 0)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this@ViewFoodActivity.finish()
    }
}