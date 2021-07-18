package com.weather.data.network

import com.weather.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitClient @Inject constructor() {

    fun <Api> getRetrofitInstance(
        api: Class<Api>,
    ): Api {
        return Retrofit.Builder().baseUrl(Constants.DEFAULTS.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(api)
    }
}
