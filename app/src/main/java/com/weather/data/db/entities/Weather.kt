package com.weather.data.db.entities

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity
data class WeatherData(
    @Embedded(prefix = "current")
    val current: Current,
    val hourly: List<Hourly> =  emptyList(),
    val lat: Double,
    val lon: Double,
    @PrimaryKey(autoGenerate = false)
    val timezone: String,
    val timezone_offset: Int


)


class HourlyTypeConvertor {


    @TypeConverter
    fun fromString(value: String?): List<Hourly> {
        val lisType = object : TypeToken<List<Hourly>>() {}.type
        return Gson().fromJson(value, lisType)

    }

    @TypeConverter
    fun fromArrayList(value: List<Hourly>?): String {
        return Gson().toJson(value)

    }
}
data class Current(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    val weather: List<Weather> =  emptyList(),
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double
)
data class Weather(
    val description: String?="",
    val icon: String?="",
    val id: Int?=0,
    val main: String?=""
)



data class Hourly(

    var clouds: Int?=0,
    var dew_point: Double?=0.0,
    var dt: Int?=0,
    var feels_like: Double?=0.0,
    var humidity: Int?=0,
    var pop: Double?=0.0,
    var pressure: Int?=0,
    @Embedded(prefix = "rain")
    var rain: Rain?,
    var temp: Double?=0.0,
    var uvi: Double?=0.0,
    var visibility: Int?=0,
    @Embedded(prefix = "weatherx")
    var weather: List<WeatherX> = emptyList(),
    var wind_deg: Int?=0,
    var wind_gust: Double?=0.0,
    var wind_speed: Double?=0.0
)

class CurrentConvertor {


    @TypeConverter
    fun fromString(value: String?): List<Weather> {
        val lisType = object : TypeToken<List<Weather>>() {}.type
        return Gson().fromJson(value, lisType)

    }

    @TypeConverter
    fun fromArrayList(value: List<Weather>?): String {
        return Gson().toJson(value)

    }
}

class WeatherXTypeConvertor {

    @TypeConverter
    fun fromString(value: String?): ArrayList<WeatherX> {
        val lisType = object : TypeToken<ArrayList<WeatherX>>() {}.type
        return Gson().fromJson(value, lisType)

    }

    @TypeConverter
    fun fromArrayList(value: ArrayList<WeatherX>?): String {
        return Gson().toJson(value)

    }
}

data class Rain(

    var `1h`: Double?=0.0
)

data class WeatherX(

    var description: String?="",
    var icon: String?="",
    var id: Int?=0,
    var main: String?=""
)