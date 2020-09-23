package com.example.vakhitov_sample.data.network.response

import com.example.vakhitov_sample.data.db.entity.CurrentWeatherEntry
import com.example.vakhitov_sample.data.db.entity.Location
import com.example.vakhitov_sample.data.db.entity.Request
import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: Location,
    val request: Request
)