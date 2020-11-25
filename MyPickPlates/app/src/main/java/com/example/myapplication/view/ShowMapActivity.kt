package com.example.myapplication.view

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_show_map.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.io.IOException
import java.util.*


class ShowMapActivity : AppCompatActivity() {
    private var mylocation = DoubleArray(2, { 0.0 })
    private var road_add = Array<String>(5, { "1" })
    private var latlong = Array<Double>(2, { 0.0 })
    private var store_name = Array<String>(5, { "1" })
    private var store_latlong = Array<Array<Double>>(5, { latlong })
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_map)

        mylocation = intent.getDoubleArrayExtra("lat_long")
        road_add = intent.getStringArrayExtra("road_add")
        store_name = intent.getStringArrayExtra("store_name")


        // 가게 이름에 <b>, </b> 제거
        for (i in 0 until 5) {
            var temp1 = store_name[i].replace("<b>", "")
            var temp = temp1.replace(">", "")
            store_name[i] = temp.replace("</b", "")
        }


        for (i in 0 until 5) {
            store_latlong[i] = address_to_latlon(road_add[i])
            Log.d("좌표로 저장하기 성공", ": " + road_add[i])
        }


        val mapView = MapView(this)
        val mapViewContainer = map_view as ViewGroup

        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(mylocation[0], mylocation[1]), true)
        
        val marker = MapPOIItem()
        marker.itemName = "sample"
        marker.tag = 0
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(store_latlong[4][0], store_latlong[4][1])
        marker.markerType = MapPOIItem.MarkerType.RedPin
        marker.selectedMarkerType = MapPOIItem.MarkerType.YellowPin
        mapView.addPOIItem(marker)

        var store_marker = arrayOf(marker, marker, marker, marker, marker)
        for (i in 0 until 5){
            store_marker[i].itemName = store_name[i]
            store_marker[i].itemName = store_name[i]
            store_marker[i].tag = 0
            store_marker[i].mapPoint = MapPoint.mapPointWithGeoCoord(store_latlong[i][0], store_latlong[i][1])
            store_marker[i].markerType = MapPOIItem.MarkerType.RedPin
            store_marker[i].selectedMarkerType = MapPOIItem.MarkerType.YellowPin
            mapView.addPOIItem(store_marker[i])
        }


        val marker_mylocation = MapPOIItem()
        marker_mylocation.itemName = "현 위치"
        marker_mylocation.tag = 0
        marker_mylocation.mapPoint = MapPoint.mapPointWithGeoCoord(mylocation[0], mylocation[1])
        marker_mylocation.markerType = MapPOIItem.MarkerType.BluePin
        marker_mylocation.selectedMarkerType = MapPOIItem.MarkerType.YellowPin
        mapView.addPOIItem(marker_mylocation)




        mapViewContainer.addView(mapView)


//  키 해시 얻는 코드 - 필요할 때 onCreate 밑에 적어서 사용
//  카카오 디벨로퍼 애플리케이션 등록시 필요한 코드
//        try {
//            val info: PackageInfo =
//                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
//            for (signature in info.signatures) {
//                val md: MessageDigest = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                Log.d("키해시는 :", Base64.encodeToString(md.digest(), Base64.DEFAULT))
//            }
//        } catch (e: PackageManager.NameNotFoundException) {
//            e.printStackTrace()
//        } catch (e: NoSuchAlgorithmException) {
//            e.printStackTrace()
//        }
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