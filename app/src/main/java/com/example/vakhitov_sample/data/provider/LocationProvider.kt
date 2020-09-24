package com.example.vakhitov_sample.data.provider

import com.example.vakhitov_sample.data.db.entity.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation) : Boolean
    suspend fun getPrefferedLocationString() : String
}