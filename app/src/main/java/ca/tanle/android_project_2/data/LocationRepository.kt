package ca.tanle.android_project_2.data

import kotlinx.coroutines.flow.Flow

class LocationRepository(private val locationDao: LocationDao) {
    suspend fun addLocation(location: Location){
        locationDao.addLocation(location)
    }

    suspend fun getAllLocation(): List<Location>{
        return locationDao.getAllLocations()
    }

    suspend fun getALocationById(id: Long): Location{
        return locationDao.getALocationById(id)
    }

    suspend fun updateLocation(location: Location){
        locationDao.updateLocation(location)
    }

    suspend fun deleteLocation(location: Location){
        locationDao.deleteLocation(location)
    }
}