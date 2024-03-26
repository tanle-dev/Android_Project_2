package ca.tanle.android_project_2.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addLocation(locationDataEntity: LocationData)

    // Load all locations from database
    @Query("Select * from `location-table`")
    suspend fun getAllLocations(): List<LocationData>

    // Update location
    @Update
    suspend fun updateLocation(locationData: LocationData)

    // Delete location from database
    @Delete
    suspend fun deleteLocation(locationDataEntity: LocationData)

    // Load location by id
    @Query("Select * from `location-table` where id=:id")
    suspend fun getALocationById(id: Long): LocationData
}