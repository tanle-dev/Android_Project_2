package ca.tanle.android_project_2


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tanle.android_project_2.data.Graph
import ca.tanle.android_project_2.data.Location
import ca.tanle.android_project_2.data.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LocationViewModel(val locationRepository: LocationRepository = Graph.locationRepository): ViewModel() {
    lateinit var getAllLocations: List<Location>
    init {
        viewModelScope.launch {
            getAllLocations = locationRepository.getAllLocation()
        }
    }

    fun getALocationById(id: Long): Flow<Location>{
        return locationRepository.getALocationById(id)
    }

    fun addWish(location: Location){
        viewModelScope.launch(Dispatchers.IO) {
            locationRepository.addLocation(location)
        }
    }

    fun updateWish(location: Location){
        viewModelScope.launch(Dispatchers.IO){
            locationRepository.updateLocation(location = location)
        }
    }

    fun deleteWish(location: Location){
        viewModelScope.launch(Dispatchers.IO) {
            locationRepository.deleteLocation(location = location)
        }
    }
}