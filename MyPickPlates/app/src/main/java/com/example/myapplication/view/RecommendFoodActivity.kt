package com.example.myapplication.view


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_recommed_food.*
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.net.URL
import java.net.URLEncoder
import java.util.*



class RecommendFoodActivity : AppCompatActivity() {

    private lateinit var food_name: String
    private var time: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommed_food)

        food_name = intent.getStringExtra("food_name")


        if (intent.hasExtra("food_name")) {
            tv_search_food.setText(food_name)

            Log.d("food_name", " : " + food_name)

            // 주소 구하기
            val latlong: Array<Double> = getCurrentLoc()
            Log.d("lattitude", " : " + latlong[0])
            Log.d("longtitude", " : " + latlong[1])

            val sigudong = korean_location(latlong)
            val sigudong_arr = sigudong.split(" ")

            add_sigudong.text = sigudong_arr[0] + " " + sigudong_arr[1] + " " + sigudong_arr[2]

            // 검색어 생성
            val search_text: String = sigudong_arr[2] + " " + food_name + " " + "맛집"
            Log.d("text information : ", "--" + search_text)

            // API 호출출
            fetchJson(search_text)


        } else {
            Toast.makeText(this, "Image Error!", Toast.LENGTH_SHORT).show()
        }


    }

//    public void onBackPressed(){
//        if(System.currentTimeMillis()-time>=2000){
//            time=System.currentTimeMillis();
//            Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show();
//        }else if(System.currentTimeMillis()-time<2000){
//            finish();
//        }
//    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    // 내 위치 위도, 경도 구하기
    fun getCurrentLoc(): Array<Double> {
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnabled: Boolean = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled: Boolean = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        var getLatitude: Double
        var getLongitude: Double
        var lat_long = Array<Double>(2, { 0.0 })
        Log.d("getcurrentloc(1)", "성공")

        // 권환 확인
        if (Build.VERSION.SDK_INT >= 23 &&
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
            Log.d("getcurrentloc(2)", "성공")
        } else {
            when { // 프로바이더 제공자 활성화 여부 체크
                isNetworkEnabled -> {
                    val location =
                        lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) // 인터넷 기반으로 위치 찾기
                    getLongitude = location?.longitude!!
                    getLatitude = location?.latitude!!
                    Log.d("getLongitude", " : " + getLongitude)
                    Log.d("getLatitude", " : " + getLatitude)
                    Toast.makeText(applicationContext, "현재 위치를 불러옵니다.", Toast.LENGTH_SHORT)
                    lat_long[0] = getLatitude
                    lat_long[1] = getLongitude
                }
                isGPSEnabled -> {
                    val location =
                        lm.getLastKnownLocation(LocationManager.GPS_PROVIDER) // 인터넷 기반으로 위치 찾기
                    getLongitude = location.longitude
                    getLatitude = location.latitude
                    Log.d("getLongitude", " : " + getLongitude)
                    Log.d("getLatitude", " : " + getLatitude)
                    Toast.makeText(applicationContext, "현재 위치를 불러옵니다.", Toast.LENGTH_SHORT)
                    lat_long[0] = getLatitude
                    lat_long[1] = getLongitude

                    Log.d("---lattitude", " : " + lat_long[0])
                    Log.d("---longtitude", " : " + lat_long[1])
                }
                else -> {
                }

            }

        }


        return lat_long

    }

    // 위도, 경도 --> 주소 구하기
    fun korean_location(lat_long: Array<Double>): String {
        var mGeocoder = Geocoder(applicationContext, Locale.KOREAN)
        var address: List<Address>? = null
        val result_add: String
        try {
            address = mGeocoder.getFromLocation(lat_long[0], lat_long[1], 1)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("test", "입출력 오류")
        }
        if (address != null) {
            Log.d("주소 : ", "--> " + address[0].subLocality)
            result_add =
                address[0].adminArea + " " + address[0].subLocality + " " + address[0].thoroughfare
            return result_add
        }
        return "현재 주소를 불러오지 못했습니다."
    }

    // 검색 API
    fun fetchJson(vararg p0: String) {
        val clienId = "3K8W6X_L4NYgoW6iMic9"
        val clienSecret = "czVKe5PHUn"

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


                val homefeed = gson.fromJson(body, Homefeed::class.java)
                println(homefeed)
                Log.d("dataJson :", "kkk" + homefeed)


                runOnUiThread {
                    val adapter =
                        RecyclerViewAdapter(
                            homefeed,
                            LayoutInflater.from(this@RecommendFoodActivity)
                        )
                    recyclerView.layoutManager = LinearLayoutManager(this@RecommendFoodActivity)
                    recyclerView.adapter = adapter
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
//            val link: TextView
            val category: TextView

            //        val description : TextView
        val telephone : TextView
            val address: TextView
//        val roadAddress : TextView
//        val mapx : TextView
//        val mapy : TextView

            init {
                title = itemView.findViewById(R.id.restaurant_name)
                category = itemView.findViewById(R.id.category)
                telephone = itemView.findViewById(R.id.telephon_number)
                address = itemView.findViewById(R.id.address)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = inflater.inflate(R.layout.item_recommend_food, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return homefeed.items.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var title = (homefeed.items.get(position).title).replace("</b>", " ")
            title = title.replace("<b>", " ")
            var category = (homefeed.items.get(position).category).replace("</b>", " ")
            category = category.replace("<b>", " ")
            var address = (homefeed.items.get(position).address).replace("</b>", " ")
            address = address.replace("<b>", " ")
            var telephone = (homefeed.items.get(position).telephone).replace("</b>", " ")
            telephone = telephone.replace("<b>", " ")

            holder.title.setText(title)
            holder.category.setText(category)
            holder.address.setText(address)
            holder.telephone.setText(telephone)
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