package com.example.vakhitov_sample.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vakhitov_sample.data.db.entity.CURRENT_LOCATION_ID
import com.example.vakhitov_sample.data.db.entity.CURRENT_WEATHER_ID
import com.example.vakhitov_sample.data.db.entity.CurrentWeatherEntry
import com.example.vakhitov_sample.data.db.entity.Location

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherDate: Location)

   @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherInfo(): LiveData<CurrentWeatherEntry>

   @Query("select * from current_location where id = $CURRENT_LOCATION_ID")
    fun getWeatherLocation(): LiveData<Location>

}