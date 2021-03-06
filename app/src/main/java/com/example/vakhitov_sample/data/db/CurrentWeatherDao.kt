package com.example.vakhitov_sample.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vakhitov_sample.data.db.entity.CURRENT_LOCATION_ID
import com.example.vakhitov_sample.data.db.entity.CURRENT_WEATHER_ID
import com.example.vakhitov_sample.data.db.entity.CurrentWeatherEntry
import com.example.vakhitov_sample.data.db.entity.WeatherLocation

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

   @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherInfo(): LiveData<CurrentWeatherEntry>
}