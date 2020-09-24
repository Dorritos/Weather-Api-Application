package com.example.vakhitov_sample.data.provider

import com.example.vakhitov_sample.data.db.entity.WeatherLocation

class LocationProviderImpl : LocationProvider {
    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        return true
    }

    override suspend fun getPrefferedLocationString(): String {
        return "Kazan"
    }
}