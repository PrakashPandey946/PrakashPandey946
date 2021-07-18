package com.weather.data.repository

import com.weather.data.db.dao.WeatherDao
import com.weather.data.db.entities.WeatherData
import com.weather.data.network.ApiServices
import com.weather.util.Constants
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiServices: ApiServices,
    private val weatherDao: WeatherDao,

) {

        suspend fun getWeatherData(): WeatherData {
            return apiServices.getWeather(
                AppPreferences.lat?:"37.0902",
                AppPreferences.long?:"95.7129",
                Constants.DEFAULTS.EXCULDE_VALUE,
                Constants.DEFAULTS.API_KEY
            )
        }

        suspend fun saveWeatherData(weatherData: WeatherData) =
            weatherDao.saveWeather(weatherData)


        fun getSavedWeather() = weatherDao.getWeather()



}