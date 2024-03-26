package ca.tanle.android_project_2.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
abstract class LocationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addLocation(locationDataEntity: LocationData)

    // Load all locations from database
    @Query("Select * from `location-table`")
    abstract suspend fun getAllLocations(): List<LocationData>

    // Update location
    @Update
    abstract suspend fun updateLocation(locationData: LocationData)

    // Delete location from database
    @Delete
    abstract suspend fun deleteLocation(locationDataEntity: LocationData)

    // Load location by id
    @Query("Select * from `location-table` where id=:id")
    abstract suspend fun getALocationById(id: Long): LocationData
}