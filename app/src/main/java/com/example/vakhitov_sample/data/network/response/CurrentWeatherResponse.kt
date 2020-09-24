package com.example.vakhitov_sample.data.network.response

import com.example.vakhitov_sample.data.db.entity.CurrentWeatherEntry
import com.example.vakhitov_sample.data.db.entity.WeatherLocation
import com.example.vakhitov_sample.data.db.entity.Request
import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    @SerializedName("location")
    val weatherLocation: WeatherLocation,
    val request: Request
)