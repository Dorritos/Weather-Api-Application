package com.example.vakhitov_sample.data.repository

import androidx.lifecycle.LiveData
import com.example.vakhitov_sample.data.db.CurrentLocationDao
import com.example.vakhitov_sample.data.db.CurrentWeatherDao
import com.example.vakhitov_sample.data.db.entity.CurrentWeatherEntry
import com.example.vakhitov_sample.data.db.entity.WeatherLocation
import com.example.vakhitov_sample.data.network.WeatherNetworkDataSource
import com.example.vakhitov_sample.data.network.response.CurrentWeatherResponse
import com.example.vakhitov_sample.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl (
    private val currentWeatherDao: CurrentWeatherDao,
    private val currentLocationDao: CurrentLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
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

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO) {
            return@withContext currentLocationDao.getLocation()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch (Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            currentLocationDao.upsert(fetchedWeather.weatherLocation)
        }
    }

    private suspend fun initWeatherData() {
        val lastWeatherLocation = currentLocationDao.getLocation().value

        if (lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation))
        {
            fetchCurrentWeather()
            return
        }

        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
        {
            fetchCurrentWeather()
        }
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPrefferedLocationString()
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore((thirtyMinutesAgo))
    }

}