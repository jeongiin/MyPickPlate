package com.example.myapplication.view


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_recommed_food.*
import android.Manifest
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
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
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.utils.MyJobService
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.item_recommend_food.*
import net.daum.mf.map.common.net.HeaderItem
import okhttp3.*
import java.io.IOException
import java.net.URL
import java.net.URLEncoder
import java.util.*
import java.util.EnumSet.range
import java.util.concurrent.TimeUnit
import android.os.AsyncTask
import android.os.Handler


class RecommendFoodActivity : AppCompatActivity() {

    private lateinit var food_name: String
    private var time: Long = 0
    private var road_add = Array<String>(5, { "1" })
    private var store_name = Array<String>(5, { "2" })
    private var latlong = DoubleArray(2, { 0.0 })
    private lateinit var store_list1: Array<Item>
    private lateinit var store_list2: Array<Item>

    //퍼미션 응답 처리 코드
    private val multiplePermissionsCode = 100

    //필요한 퍼미션 리스트
    //원하는 퍼미션을 이곳에 추가하면 된다.
    private val requiredPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommed_food)


        checkPermissions()

        food_name = intent.getStringExtra("food_name")


        if (intent.hasExtra("food_name")) {
            tv_search_food.setText(food_name)

            Log.d("food_name", " : " + food_name)

            // 주소 구하기
            latlong = getCurrentLoc()
            Log.d("lattitude", " : " + latlong[0])
            Log.d("longtitude", " : " + latlong[1])

            val sigudong = korean_location(latlong)
            val sigudong_arr = sigudong.split(" ")

            add_sigudong.text = sigudong_arr[0] + " " + sigudong_arr[1] + " " + sigudong_arr[2]


            // 검색어 생성
            val search_text1: String = sigudong_arr[2] + " " + food_name + " " + "맛집"
            Log.d("text information : ", "--" + search_text1)

            val search_text2: String = sigudong_arr[2] + " " + food_name + " " + "존맛"
            Log.d("text information : ", "--" + search_text2)

            // API 호출
            fetchJson(search_text1)

        } else {
            Toast.makeText(this, "Image Error!", Toast.LENGTH_SHORT).show()
        }

        bt_show_map.setOnClickListener {
            val intent = Intent(this, ShowMapActivity::class.java)
            intent.apply {
                this.putExtra("lat_long", latlong)
                this.putExtra("road_add", road_add)
                this.putExtra("store_name", store_name)
            }

            startActivity(intent)
        }


    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    //퍼미션 체크 및 권한 요청 함수
    private fun checkPermissions() {
        //거절되었거나 아직 수락하지 않은 권한(퍼미션)을 저장할 문자열 배열 리스트
        var rejectedPermissionList = ArrayList<String>()

        //필요한 퍼미션들을 하나씩 끄집어내서 현재 권한을 받았는지 체크
        for (permission in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                //만약 권한이 없다면 rejectedPermissionList에 추가
                rejectedPermissionList.add(permission)
            }
        }
        //거절된 퍼미션이 있다면...
        if (rejectedPermissionList.isNotEmpty()) {
            //권한 요청!
            val array = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(
                this,
                rejectedPermissionList.toArray(array),
                multiplePermissionsCode
            )
        }
    }

    // 내 위치 위도, 경도 구하기
    fun getCurrentLoc(): DoubleArray {
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnabled: Boolean = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled: Boolean = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        var getLatitude: Double
        var getLongitude: Double
        var lat_long = DoubleArray(2, { 0.0 })
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
    fun korean_location(lat_long: DoubleArray): String {
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

                var k = 0
                for ( i in homefeed.items){
                    road_add[k] = i.roadAddress
                    store_name[k] = i.title
                    k+=1
                }


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
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView
            val category: TextView
            val telephone: TextView
            val address: TextView

            init {
                title = itemView.findViewById(R.id.restaurant_name)
                category = itemView.findViewById(R.id.category)
                telephone = itemView.findViewById(R.id.telephon_number)
                address = itemView.findViewById(R.id.address)
                itemView.setOnClickListener {
                    val position: Int = adapterPosition
                    val selected_title = homefeed.items.get(position).title
                    val selected_address = homefeed.items.get(position).address

                    val intentSelectedStoreShowMap =
                        Intent(itemView.context, SelectedStoreShowMap::class.java)
                    intentSelectedStoreShowMap.apply {
                        this.putExtra("selected_title", selected_title)
                        this.putExtra("selected_address", selected_address)
                    }
                    ContextCompat.startActivity(itemView.context, intentSelectedStoreShowMap, null)
                    Log.d("셋온클릭한 다음 ", " " + selected_address)
                }
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
            var title = (homefeed.items.get(position).title).replace("</b>", "")
            title = title.replace("<b>", " ")
            var category = (homefeed.items.get(position).category).replace("</b>", "")
            category = category.replace("<b>", " ")
            var address = (homefeed.items.get(position).address).replace("</b>", "")
            address = address.replace("<b>", " ")
            var telephone = (homefeed.items.get(position).telephone).replace("</b>", "")
            telephone = telephone.replace("<b>", " ")

            Log.d("이름은 잘 들어갔는가", title)
            Log.d("이름은 잘 들어갔는가", category)
            Log.d("이름은 잘 들어갔는가", address)
            Log.d("이름은 잘 들어갔는가", telephone)


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
    val mapx: Double,
    val mapy: Double
)