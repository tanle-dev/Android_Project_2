package ca.tanle.android_project_2.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("location-table")
data class Location (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo("lat")
    val latitude: Double = 0.0,
    @ColumnInfo("lon")
    val longitude: Double = 0.0,
    @ColumnInfo("place")
    val placeName: String = "",
    @ColumnInfo("desc")
    val placeDesc: String = "",
    @ColumnInfo("street")
    val street: String = "",
    @ColumnInfo("city")
    val city: String = "",
    @ColumnInfo("state")
    val state: String = "",
    @ColumnInfo("country")
    val country: String = "",
    @ColumnInfo("zipcode")
    val zipCode: String = "",
)