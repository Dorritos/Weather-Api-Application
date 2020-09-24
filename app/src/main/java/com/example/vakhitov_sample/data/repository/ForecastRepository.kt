package com.example.vakhitov_sample.data.repository

import androidx.lifecycle.LiveData
import com.example.vakhitov_sample.data.db.entity.CurrentWeatherEntry
import com.example.vakhitov_sample.data.db.entity.WeatherLocation

interface ForecastRepository {
    suspend fun getCurrentWeather() : LiveData<CurrentWeatherEntry>
    suspend fun getWeatherLocation() : LiveData<WeatherLocation>
}