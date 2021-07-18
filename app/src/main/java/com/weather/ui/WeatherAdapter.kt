package com.weather.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.weather.R
import com.weather.data.db.entities.Hourly
import com.weather.databinding.RecyclerviewWeatherBinding
import com.weather.util.AppUtils

class WeatherAdapter(
    private val weathers: List<Hourly>
) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WeatherViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recyclerview_weather, parent, false
            )
        )


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.recyclerviewWeatherBinding.tvTemp.text =
            "Temperature : " + AppUtils.getCelsiusFromKelvin(weathers[position].temp?.toString()!!)
                .toString()
        holder.recyclerviewWeatherBinding.tvHour.text =
            "Hour : " + AppUtils.formatTime(weathers[position].dt?.toLong()!!).toString()

        holder.recyclerviewWeatherBinding.tvWindSpeed.text =
            "Wind Speed : " + weathers[position].wind_speed.toString()
        holder.recyclerviewWeatherBinding.tvHumidity.text =
            "Humidity : " + weathers[position].humidity.toString()
    }

    override fun getItemCount(): Int {
        return weathers.size
    }

    inner class WeatherViewHolder(val recyclerviewWeatherBinding: RecyclerviewWeatherBinding) :
        RecyclerView.ViewHolder(recyclerviewWeatherBinding.root)

}