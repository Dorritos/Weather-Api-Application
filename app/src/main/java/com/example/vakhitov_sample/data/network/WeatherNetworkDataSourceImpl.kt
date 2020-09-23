package com.example.vakhitov_sample.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vakhitov_sample.data.network.response.CurrentWeatherResponse
import com.example.vakhitov_sample.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl (
    private val WeatherStackApi: WeatherStackApi
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String) {
        try {
            val fetchedCurrentWeather = WeatherStackApi
                .getCurrentWeather(location)
                .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "NO INTERNET, DOLBOEB", e)
        }
    }
}