package com.example.vakhitov_sample.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vakhitov_sample.data.db.entity.CURRENT_LOCATION_ID
import com.example.vakhitov_sample.data.db.entity.WeatherLocation

@Dao
interface CurrentLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherDate: WeatherLocation)

    @Query("select * from current_location where id = $CURRENT_LOCATION_ID")
    fun getLocation(): LiveData<WeatherLocation>
}