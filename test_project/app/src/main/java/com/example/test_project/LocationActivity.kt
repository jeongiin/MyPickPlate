package com.example.test_project

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_location.*
import java.io.IOException
import java.util.*

var locationManager: LocationManager? = null
val REQUEST_CODE_LOCATION: Int = 2
var currentLocation: String = ""
var latitude: Double? = null
var longitude: Double? = null

class LocationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        button_search_location.setOnClickListener {
            getCurrentLoc()
            Toast.makeText(applicationContext, "현재 위치를 불러옵니다", Toast.LENGTH_SHORT)
        }

    }


    fun getCurrentLoc() {

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        var userLocation: Location = getLatLng()
        if (userLocation != null) {
            latitude = userLocation.latitude
            longitude = userLocation.longitude
            Log.d("ChcekCurrentLocation:", "현재 위치 값: $latitude, $longitude")

            var mGeocoder = Geocoder(applicationContext, Locale.KOREAN)
            var mResultList: List<Address>? = null
            try {
                mResultList = mGeocoder.getFromLocation(
                    latitude!!, longitude!!, 1
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (mResultList != null) {

                currentLocation = mResultList[0].getAddressLine(0)
                currentLocation = currentLocation.substring(15,19)
                Log.d("CheckCurrentLocation", mResultList[0].getAddressLine(0))
                my_location.setText(currentLocation)
            }
        } else {
            Toast.makeText(applicationContext, "현재 위치를 탐색하지 못했습니다.", Toast.LENGTH_SHORT)
        }
    }

    fun getLatLng(): Location {
        var currentLatLng: Location? = null
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                2
            )
            getLatLng()
        } else {
            val locationProvider = LocationManager.GPS_PROVIDER
            currentLatLng = locationManager?.getLastKnownLocation(locationProvider)
        }
        return currentLatLng!!
    }

}