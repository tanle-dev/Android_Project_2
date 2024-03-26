package ca.tanle.android_project_2.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LocationViewModal (private val locationRepository: LocationRepository) : ViewModel() {

    suspend fun addLocation(location: Location){
        locationRepository.addLocation(location)
    }

    suspend fun getAllLocation(): List<Location>{
        return locationRepository.getAllLocation()
    }

    suspend fun getALocationById(id: Long): Location{
        return locationRepository.getALocationById(id)
    }

    suspend fun updateLocation(location: Location){
        locationRepository.updateLocation(location)
    }

    suspend fun deleteLocation(location: Location){
        locationRepository.deleteLocation(location)
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