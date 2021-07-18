package com.weather.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.weather.data.db.entities.WeatherData

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveWeather(weatherData: WeatherData)

    @Query("SELECT * FROM weatherdata")
    fun getWeather(): LiveData<List<WeatherData>>


}