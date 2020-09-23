package com.example.vakhitov_sample.data.db.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
    @ColumnInfo(name = "cloudcover")
    val cloudcover: Int,

    @ColumnInfo(name = "feelslike")
    val feelslike: Int,

    @ColumnInfo(name = "humidity")
    val humidity: Int,

    @ColumnInfo(name = "isDay")
    @SerializedName("is_day")
    val isDay: String,

    @ColumnInfo(name = "observationTime")
    @SerializedName("observation_time")
    val observationTime: String,

    @ColumnInfo(name = "precip")
    val precip: Double,

    @ColumnInfo(name = "pressure")
    val pressure: Int,

    @ColumnInfo(name = "temperature")
    val temperature: Int,

    @ColumnInfo(name = "uvIndex")
    @SerializedName("uv_index")
    val uvIndex: Int,

    @ColumnInfo(name = "visibility")
    val visibility: Int,

    @ColumnInfo(name = "weatherCode")
    @SerializedName("weather_code")
    val weatherCode: Int,

    /*
    @SerializedName("weather_descriptions")
    val weatherDescriptions: List<String>,
    @SerializedName("weather_icons")
    val weatherIcons: List<String>,
    */

    @ColumnInfo(name = "windDegree")
    @SerializedName("wind_degree")
    val windDegree: Int,

    @ColumnInfo(name = "windDir")
    @SerializedName("wind_dir")
    val windDir: String,

    @ColumnInfo(name = "windSpeed")
    @SerializedName("wind_speed")
    val windSpeed: Int
)
{
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}