package com.weather.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.weather.data.db.dao.WeatherDao
import com.weather.data.db.entities.*

@Database(entities = [WeatherData::class], version = 1,exportSchema = false)
@TypeConverters(HourlyTypeConvertor::class,WeatherXTypeConvertor::class,CurrentConvertor::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getWeatherDao(): WeatherDao

}