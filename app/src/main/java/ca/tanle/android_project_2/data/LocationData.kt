package ca.tanle.android_project_2.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("location-table")
data class LocationData (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo("lat")
    var latitude: Double = 0.0,
    @ColumnInfo("lon")
    var longitude: Double = 0.0,
    @ColumnInfo("place")
    var placeName: String = "",
    @ColumnInfo("desc")
    var placeDesc: String = "",
    @ColumnInfo("street")
    var street: String = "",
    @ColumnInfo("city")
    var city: String = "",
    @ColumnInfo("state")
    var state: String = "",
    @ColumnInfo("country")
    var country: String = "",
    @ColumnInfo("zipcode")
    var zipCode: String = "",
)