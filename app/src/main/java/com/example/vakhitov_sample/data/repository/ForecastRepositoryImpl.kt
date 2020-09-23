package com.example.vakhitov_sample.data.repository

import androidx.lifecycle.LiveData
import com.example.vakhitov_sample.data.db.CurrentWeatherDao
import com.example.vakhitov_sample.data.db.entity.CurrentWeatherEntry
import com.example.vakhitov_sample.data.db.entity.Location
import com.example.vakhitov_sample.data.network.WeatherNetworkDataSource
import com.example.vakhitov_sample.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl (
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
): ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext currentWeatherDao.getWeatherInfo()
        }
    }

    override suspend fun getCurrentDate(): LiveData<Location> {
        return withContext(Dispatchers.IO) {
           initWeatherData()
            return@withContext currentWeatherDao.getWeatherLocation()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch (Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            currentWeatherDao.upsert(fetchedWeather.location)
        }
    }

    private suspend fun initWeatherData() {
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(
            "Moscow"
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore((thirtyMinutesAgo))
    }

}