package com.example.vakhitov_sample.data.db.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

const val CURRENT_LOCATION_ID = 0

@Entity(tableName = "current_location")
data class WeatherLocation(
    @ColumnInfo(name = "country")
    val country: String,

    @ColumnInfo(name = "lat")
    val lat: Double,

    @ColumnInfo(name = "localtime")
    val localtime: String,

    @ColumnInfo(name = "localtimeEpoch")
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Long,

    @ColumnInfo(name = "lon")
    val lon: Double,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "region")
    val region: String,

    @ColumnInfo(name = "timezoneId")
    @SerializedName("timezone_id")
    val timezoneId: String,

    @ColumnInfo(name = "utcOffset")
    @SerializedName("utc_offset")
    val utcOffset: String
)
{
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_LOCATION_ID

    val zonedDateTime: ZonedDateTime
        get() {
            val instant = Instant.ofEpochSecond(localtimeEpoch)
            val zoneId = ZoneId.of(timezoneId)
            return ZonedDateTime.ofInstant(instant, zoneId)
        }
}