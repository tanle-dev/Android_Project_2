package ca.tanle.android_project_2.data

class LocationRepository(private val locationDao: LocationDao) {
    suspend fun addLocation(locationData: LocationData){
        locationDao.addLocation(locationData)
    }

    suspend fun getAllLocation(): List<LocationData>{
        return locationDao.getAllLocations()
    }

    suspend fun getALocationById(id: Long): LocationData{
        return locationDao.getALocationById(id)
    }

    suspend fun updateLocation(locationData: LocationData){
        locationDao.updateLocation(locationData)
    }

    suspend fun deleteLocation(locationData: LocationData){
        locationDao.deleteLocation(locationData)
    }
}