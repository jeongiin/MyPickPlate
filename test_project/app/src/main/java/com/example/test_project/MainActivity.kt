package com.example.test_project

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_location.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.w3c.dom.Text
import java.io.IOException
import java.net.URL
import java.net.URLEncoder
import java.util.*

// 정인이가 넘겨준 음식 이름 어떻게 받는 지 확인하기
// 앱 잘 돌아가는지 확인하기 위한 예시 추후 삭제 필수
var foodname: String = " 된장찌개 "

class MainActivity : AppCompatActivity() {
    val clienId = "3K8W6X_L4NYgoW6iMic9"
    val clienSecret = "czVKe5PHUn"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("whereeeee", "nonhere")

//        var test_dong : String = getCurrentLoc()
//        Log.d("checking","dong :"+test_dong)

        button_search.setOnClickListener({
            Log.d("bbutton", "check")

//            var search_text: String = getCurrentLoc() + foodname + "맛집"
            Log.d("search text: ", "000" )

            // 키워드가 없으면
            if (search_dong.text.isEmpty()) {
                Toast.makeText(applicationContext, "동을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
//
//            // 레이아웃 매니저 설정
//
//
            // API
            fetchJson(foodname)


            // 키보드를 내린다
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(search_dong.windowToken, 0)

        })

    }

    // 위치 주소로 찾기
//    fun getCurrentLoc(): String {
//        var locationManager: LocationManager? = null
//        val REQUEST_CODE_LOCATION: Int = 2
//        var currentLocation: String = ""
//        var latitude: Double? = null
//        var longitude: Double? = null
//        var dong: String = "fgfh"
//
//        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
//        var userLocation: Location = getLatLng()
//        if (userLocation != null) {
//            latitude = userLocation.latitude
//            longitude = userLocation.longitude
//            Log.d("ChcekCurrentLocation:", "현재 위치 값: $latitude, $longitude")
//
//            var mGeocoder = Geocoder(applicationContext, Locale.KOREAN)
//            var mResultList: List<Address>? = null
//            try {
//                mResultList = mGeocoder.getFromLocation(
//                    latitude!!, longitude!!, 1
//                )
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//            if (mResultList != null) {
////                currentLocation = mResultList[0].subThoroughfare
//
//                currentLocation = mResultList[0].getAddressLine(0)
//                var devide_add = currentLocation.split(" ")
////                currentLocation = currentLocation.substring(15,19)
//                Log.d("CheckCurrentLocation", mResultList[0].getAddressLine(0))
////                my_location.setText(devide_add[3])
//                dong = devide_add[3]
//
//            }
//        } else {
//            Toast.makeText(applicationContext, "현재 위치를 탐색하지 못했습니다.", Toast.LENGTH_SHORT)
//        }
//        return dong
//    }
//
//    fun getLatLng(): Location {
//        var currentLatLng: Location? = null
//        if (ActivityCompat.checkSelfPermission(
//                applicationContext,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//            && ActivityCompat.checkSelfPermission(
//                applicationContext,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                2
//            )
//            getLatLng()
//        } else {
//            val locationProvider = LocationManager.GPS_PROVIDER
//            currentLatLng = locationManager?.getLastKnownLocation(locationProvider)
//        }
//        return currentLatLng!!
//    }

    // 검색 API
    fun fetchJson(vararg p0: String) {
        //OkHttp로 요청하기
        val text = URLEncoder.encode("${p0[0]}", "UTF-8")
        println(text)
        val url =
            URL("https://openapi.naver.com/v1/search/local.json?query=${text}&display=10&start=1")
        val formBody =
            FormBody.Builder().add("query", "${text}").add("display", "10").add("start", "1")
                .build()
        val request = Request.Builder().url(url).addHeader("X-Naver-Client-Id", clienId)
            .addHeader("X-Naver-Client-Secret", clienSecret).method("GET", null).build()
        Log.d("checking the text", " " + p0[0])

        val client = OkHttpClient()
        Log.d("where", "the fuck")
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                Log.d("please", "help")
                val body = response?.body()?.string()
                println("Success to execute request : $body")
                Log.d("body---", "body")

                //Gson을 Kotlin에서 사용 가능한 object로 만든다.
                val gson = GsonBuilder().create()
                Log.d("buamdong", "000" + gson)

                //아! 이렇게 하는구나
                val homefeed = gson.fromJson(body, Homefeed::class.java)
                println(homefeed)
                Log.d("dataJson :", "kkk" + homefeed)

                runOnUiThread {
                    val adapter =
                        RecyclerViewAdapter(homefeed, LayoutInflater.from(this@MainActivity))
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                    recyclerView.adapter = adapter
                    search_dong.setText("")
                    Log.d("layoutttttt", "check")
                }

            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }
        })

    }


    class RecyclerViewAdapter(
        val homefeed: Homefeed,
        val inflater: LayoutInflater
    ) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView
            val link: TextView
            val category: TextView

            //        val description : TextView
//        val telephone : TextView
            val address: TextView
//        val roadAddress : TextView
//        val mapx : TextView
//        val mapy : TextView

            init {
                title = itemView.findViewById(R.id.restaurant_name)
                category = itemView.findViewById(R.id.category)
                link = itemView.findViewById(R.id.link)
                address = itemView.findViewById(R.id.address)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = inflater.inflate(R.layout.item_restaurant, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return homefeed.items.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.title.setText(homefeed.items.get(position).title)
            holder.category.setText(homefeed.items.get(position).category)
            holder.address.setText(homefeed.items.get(position).address)
            holder.link.setText(homefeed.items.get(position).link)
        }
    }
}


//data class
data class Homefeed(val items: List<Item>)
data class Item(
    val title: String,
    val link: String,
    val category: String,
    val description: String,
    val telephone: String,
    val address: String,
    val roadAddress: String,
    val mapx: Int,
    val mapy: Int
)


