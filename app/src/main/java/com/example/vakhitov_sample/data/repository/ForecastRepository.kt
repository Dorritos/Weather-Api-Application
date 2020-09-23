package com.example.vakhitov_sample.data.repository

import androidx.lifecycle.LiveData
import com.example.vakhitov_sample.data.db.entity.CurrentWeatherEntry
import com.example.vakhitov_sample.data.db.entity.Location

interface ForecastRepository {
    suspend fun getCurrentWeather() : LiveData<CurrentWeatherEntry>
    suspend fun getCurrentDate() : LiveData<Location>
}