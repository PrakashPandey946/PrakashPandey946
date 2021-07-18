package com.weather.data.network

import com.weather.data.db.entities.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {


    @GET("data/2.5/onecall?")
    suspend fun getWeather(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("exclude") exclude: String?,
        @Query("appid") app_id: String?
    ): WeatherData



}