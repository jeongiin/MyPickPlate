package com.example.test_project

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import java.net.URL
import java.net.URLEncoder
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun main(args: Array<String>) {
//    val text = URLEncoder.encode("부암동", "UTF-8")
//    val display = 5
//    println(text)
//    val url = URL("\"https://openapi.naver.com/v1/search/local.xml?query=" + text + "&display=" + display + "&")
//    val request = Request.Builder()


    val clienId = "3K8W6X_L4NYgoW6iMic9"
    val clienSecret = "czVKe5PHUn"

    //OkHttp로 요청하기
    val text = URLEncoder.encode("연신내 파스타 맛집", "UTF-8")
    println(text)
    val url = URL("https://openapi.naver.com/v1/search/local.json?query=${text}&display=20&start=1")
    val formBody = FormBody.Builder().add("query","${text}").add("display","10").add("start","1").build()
    val request = Request.Builder().url(url).addHeader("X-Naver-Client-Id", clienId).addHeader("X-Naver-Client-Secret", clienSecret).method("GET", null).build()

    val client = OkHttpClient()
    client.newCall(request).enqueue(object : Callback{
        override fun onResponse(call: Call?, response: Response?) {
            val body = response?.body()?.string()
            println("Success to execute request : $body")
            Log.d("내용","7"+body)

            //Gson을 Kotlin에서 사용 가능한 object로 만든다.
            val gson = GsonBuilder().create()

            //아! 이렇게 하는구나
            val homefeed = gson.fromJson(body, Homefeed::class.java)
            println(homefeed)

//                runOnUiThread{
//                    recyclerView.adapter = RecyclerViewAdapter(homefeed)
//                    search_dong.setText("")
//                }

        }

        override fun onFailure(call: Call?, e: IOException?) {
            println("Failed to execute request")
        }
    })
}

//
//var locationManger : LocationManger? = null
//
//
//val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//button_search_location.setOnClickListener {
//    var getLogitude : Double
//    var getLatitude : Double
//    val isGPSEnabled: Boolean = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
//    val isNetworkEnabled: Boolean = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//    // Manifest에 권한이 추가되어 있다고 해도 여기서 다시 한 번 확인해야 함
//    if (Build.VERSION.SDK_INT >= 23 &&
//        ContextCompat.checkSelfPermission(
//            applicationContext,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        ) != PackageManager.PERMISSION_GRANTED
//    ) {
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//            0
//        )
//    } else {
//        when { // 프로바이더 제공자 활성화 여부 체크
//            isNetworkEnabled -> {
//                val location =
//                    lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) // 인터넷 기반으로 위치를 찾음
//                getLogitude = location?.longitude!!
//                getLatitude = location.latitude
//                Toast.makeText(applicationContext, "현재 위치를 불러옵니다", Toast.LENGTH_SHORT)
//                    .show()
//                Log.d("locationnnnnn", "ddd"+ getLogitude +", "+getLatitude)
//            }
//            isGPSEnabled -> {
//                val location =
//                    lm.getLastKnownLocation(LocationManager.GPS_PROVIDER) //GPS 기반으로 위치를 찾음
//                getLogitude = location?.longitude!!
//                getLatitude = location.latitude
//                Toast.makeText(applicationContext, "현재 위치를 불러옵니다", Toast.LENGTH_SHORT)
//                    .show()
//            }
//            else -> {
//            }
//        }
//    }
//}
////        lm.removeUpdates(gpsLocationListner)

