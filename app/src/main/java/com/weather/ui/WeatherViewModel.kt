package com.weather.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weather.data.db.entities.WeatherData
import com.weather.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
    @Inject constructor(private var weatherRepository: WeatherRepository) : ViewModel() {


    private val _weathers=MutableLiveData<ArrayList<WeatherData>>()

    fun getSavedWeather() = weatherRepository.getSavedWeather()

    fun getWeatherData() {
        CoroutineScope(Dispatchers.Main).launch {
            val res = weatherRepository.getWeatherData()
            if (res!=null) {
              res.let {
                  _weathers.value=it.hourly as ArrayList<WeatherData>

                  CoroutineScope(Dispatchers.IO).launch {
                      weatherRepository.saveWeatherData(res)
                  } }
            }
        }

    }


}