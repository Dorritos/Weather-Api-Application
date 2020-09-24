package com.example.vakhitov_sample.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.vakhitov_sample.data.repository.ForecastRepository
import com.example.vakhitov_sample.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather()
    }
    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }


}