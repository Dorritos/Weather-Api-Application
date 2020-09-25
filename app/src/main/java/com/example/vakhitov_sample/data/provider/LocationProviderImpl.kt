package com.example.vakhitov_sample.data.provider

import android.content.pm.PackageManager
import android.content.Context
import android.location.Location
import androidx.core.content.ContextCompat
import com.example.vakhitov_sample.data.db.entity.WeatherLocation
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Deferred
import java.util.jar.Manifest

const val  USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val  CUSTOM_LOCATION = "CUSTOM_LOCATION"

class LocationProviderImpl (
    private val fusedLocationProviderClient: FusedLocationProviderClient
): LocationProvider {

    private val appContext = context.applicationContext

    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        val deviceLocationChanged = hasDeviceLocationChanged(lastWeatherLocation)
        return deviceLocationChanged || hasCustomLocationChanged(lastWeatherLocation)
    }

    override suspend fun getPrefferedLocationString(): String {
        return "Kazan"
    }

    private suspend fun hasDeviceLocationChanged(lastWeatherLocation: WeatherLocation) : Boolean {
        if (!isUsingDeviceLocation())
        {
            return false
        }
        else
        {
            val deviceLocation = getLastDeviceLocation().await
                ?: return false

            val comparisonThreshold = 0.03
            return Math.abs(deviceLocation.latitude - lastWeatherLocation.lat) > comparisonThreshold
                    &&
                    Math.abs(deviceLocation.longitude - lastWeatherLocation.lon) > comparisonThreshold
        }
    }

    private fun isUsingDeviceLocation() : Boolean {
        return preferences.getBoolean(USE_DEVICE_LOCATION, true)
    }

    private fun getLastDeviceLocation() : Deferred<Location?> {
        return fusedLocationProviderClient.lastLocation
    }

    private fun hasLocationPermission() : Boolean {
        return ContextCompat.checkSelfPermission(appContext,
            Manifest.permission.ACCES_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}