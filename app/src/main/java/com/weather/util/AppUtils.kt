package com.weather.util

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class AppUtils {

    companion object{
        fun getCelsiusFromKelvin(kelvinString: String): Double {
            val kelvin = kelvinString.toDouble()
            val numberToMinus = 273.15
            var celsius = kelvin - numberToMinus
            // Rounding up the double value
            // Each zero (0) return 1 more precision
            // Precision means number of digits after dot
            celsius = (celsius * 10).roundToInt().toDouble() / 10
            return celsius
        }

        @SuppressLint("SimpleDateFormat")
        fun formatTime(value: Long): String? {


            val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm a")

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = value*1000L
            return formatter.format(calendar.time)


        }
    }
}