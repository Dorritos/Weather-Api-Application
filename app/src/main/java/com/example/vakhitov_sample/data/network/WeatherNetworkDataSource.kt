package com.example.vakhitov_sample.data.network

import androidx.lifecycle.LiveData
import com.example.vakhitov_sample.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather (
        location: String
    )
}