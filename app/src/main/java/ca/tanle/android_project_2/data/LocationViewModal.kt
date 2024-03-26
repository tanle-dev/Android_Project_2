package ca.tanle.android_project_2.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LocationViewModal (private val locationRepository: LocationRepository) : ViewModel() {

    suspend fun addLocation(locationData: LocationData){
        locationRepository.addLocation(locationData)
    }

    suspend fun getAllLocation(): List<LocationData>{
        return locationRepository.getAllLocation()
    }

    suspend fun getALocationById(id: Long): LocationData{
        return locationRepository.getALocationById(id)
    }

    suspend fun updateLocation(locationData: LocationData){
        locationRepository.updateLocation(locationData)
    }

    suspend fun deleteLocation(locationData: LocationData){
        locationRepository.deleteLocation(locationData)
    }
}

class LocationViewModalFactory(private val locationRepository: LocationRepository) : ViewModelProvider.Factory {
     override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationViewModal::class.java)) {
            return LocationViewModal(locationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}