package com.example.myapplication.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_selected_store_show_map.*
import kotlinx.android.synthetic.main.activity_show_map.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.io.IOException
import java.util.*

class SelectedStoreShowMap : AppCompatActivity() {

    private var mylocation = DoubleArray(2, { 0.0 })
    private var title: String = "store_title"
    private var road_add: String = "a"
    private var latlong = Array<Double>(2, { 0.0 })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_store_show_map)

        title = intent.getStringExtra("selected_title")
        road_add = intent.getStringExtra("selected_address")
        mylocation = getCurrentLoc()

        // 제목에 들어가가는 내용 정리
        var temp1 = title.replace("<b>", "")
        var temp = temp1.replace(">", "")
        title = temp.replace("</b", "")

        // 도로명 주소를 좌표로 변환
        latlong = address_to_latlon(road_add)

        val mapView = MapView(this)
        val mapViewContainer = single_map_view as ViewGroup

        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(mylocation[0], mylocation[1]), true)

        val marker_mylocation = MapPOIItem()
        marker_mylocation.itemName = "현 위치"
        marker_mylocation.tag = 0
        marker_mylocation.mapPoint = MapPoint.mapPointWithGeoCoord(mylocation[0], mylocation[1])
        marker_mylocation.markerType = MapPOIItem.MarkerType.BluePin
        marker_mylocation.selectedMarkerType = MapPOIItem.MarkerType.YellowPin
        mapView.addPOIItem(marker_mylocation)

        val marker_store = MapPOIItem()
        marker_store.itemName = title
        marker_store.tag = 0
        marker_store.mapPoint = MapPoint.mapPointWithGeoCoord(latlong[0], latlong[1])
        marker_store.markerType = MapPOIItem.MarkerType.RedPin
        marker_store.selectedMarkerType = MapPOIItem.MarkerType.YellowPin
        mapView.addPOIItem(marker_store)


        mapViewContainer.addView(mapView)

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

    fun address_to_latlon(road_address: String): Array<Double> {
        var mGeocoder = Geocoder(applicationContext, Locale.KOREAN)
        var address: List<Address>? = null
        var temp = Array(2, { 0.0 })
        val result_add: String
        try {
            address = mGeocoder.getFromLocationName(road_address, 1)
            Log.d("주소 -> 좌표", ": " + address[0].latitude)
            temp[0] = address[0].latitude
            temp[1] = address[0].longitude

        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("test", "입출력 오류")
        }
        return temp
    }
}