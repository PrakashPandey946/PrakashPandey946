package com.weather

import AppPreferences
import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
    }
}