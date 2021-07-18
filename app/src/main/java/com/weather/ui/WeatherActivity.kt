package com.weather.ui

import GPSUtils
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.weather.R
import com.weather.data.network.NetworkConnection
import com.weather.databinding.ActivityWeatherBinding
import com.weather.util.AppUtils
import com.weather.util.Constants
import com.weather.util.Constants.DEFAULTS.REQUEST_LOCATION
import com.weather.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.coroutines.launch


@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {

    private val factory: WeatherViewModel by viewModels()


    @SuppressLint("MissingPermission", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityWeatherBinding: ActivityWeatherBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_weather)

        activityWeatherBinding.viewmodel = factory

        getWeatherFromDB()

        checkLocationEnable();
    }


    private fun getWeatherFromDB() {
        factory.getSavedWeather().observe(this, Observer { weathers ->
            if (weathers.size == 0)
                return@Observer


            val intent = Intent(Constants.DEFAULTS.ACTION_UPDATE)
            intent.putExtra(
                Constants.DEFAULTS.TEMP,
                AppUtils.getCelsiusFromKelvin(weathers.get(0).current.temp.toString()).toString()
            )
            applicationContext.sendBroadcast(intent)

            tv_temp.text =
                "Current Temperature : " + AppUtils.getCelsiusFromKelvin(weathers.get(0).current.temp.toString())
                    .toString()
            weather_list.also {


                it.layoutManager = LinearLayoutManager(this)
                it.setHasFixedSize(true)
                it.adapter = WeatherAdapter(weathers.get(0).hourly)
            }
        })
    }


    //Integrate the onActivityResult function for the better output
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.DEFAULTS.GPS_CODE) {
                checkLocationEnable()
            } else {
                GPSUtils(this).turnOnGPS()
            }
        } else {
            GPSUtils(this).turnOnGPS()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(
                            this, Manifest.permission.ACCESS_FINE_LOCATION
                        ) ===
                                PackageManager.PERMISSION_GRANTED)
                    ) {
                        checkLocationEnable()

                    }
                } else {
                    toast("Permission Denied")
                }
                return
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        val mLocationRequest = LocationRequest.create()
        mLocationRequest.interval = 60000
        mLocationRequest.fastestInterval = 5000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val mLocationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    if (location != null) {
                        //TODO: UI updates.
                    }
                }
            }
        }
        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(mLocationRequest, mLocationCallback, null)
        LocationServices.getFusedLocationProviderClient(this).lastLocation.addOnSuccessListener {

            if (it?.latitude != null || it?.longitude != null) {

                lifecycleScope.launch {
                    AppPreferences.lat=it.latitude.toString()
                    AppPreferences.long=it.longitude.toString()
                    factory.getWeatherData()
                }

            } else {
                lifecycleScope.launch {
                    AppPreferences.lat="37.0902"
                    AppPreferences.long="95.7129"
                    factory.getWeatherData()
                }
            }

        }
    }

    private fun checkLocationEnable() {
        val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            GPSUtils(this).turnOnGPS()
            return
        }
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        } else {
            getLastLocation()
        }
    }
}

