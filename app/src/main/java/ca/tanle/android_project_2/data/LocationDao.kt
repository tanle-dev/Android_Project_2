package ca.tanle.android_project_2.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LocationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addLocation(locationEntity: Location)

    // Load all locations from database
    @Query("Select * from `location-table`")
    abstract suspend fun getAllLocations(): List<Location>

    // Update location
    @Update
    abstract suspend fun updateLocation(location: Location)

    // Delete location from database
    @Delete
    abstract suspend fun deleteLocation(locationEntity: Location)

    // Load location by id
    @Query("Select * from `location-table` where id=:id")
    abstract suspend fun getALocationById(id: Long): Location
}