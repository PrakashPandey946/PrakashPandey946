package com.weather.data.di

import android.content.Context
import androidx.room.Room
import com.weather.data.db.AppDatabase
import com.weather.data.network.ApiServices
import com.weather.data.network.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    fun provideApi(
        retrofitClient: RetrofitClient) :ApiServices{
        return retrofitClient.getRetrofitInstance(ApiServices::class.java)

    }
    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "weather"
    ).build()

    @Singleton
    @Provides
    fun provideYourDao(db: AppDatabase) = db.getWeatherDao()
}