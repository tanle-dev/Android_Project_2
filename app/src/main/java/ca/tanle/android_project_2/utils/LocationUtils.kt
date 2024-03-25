package ca.tanle.android_project_2.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import ca.tanle.android_project_2.data.Location
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

class LocationUtils(val context: Context) {
    var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    fun hasLocationPermission(context: Context): Boolean{
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    fun requestLocationUpdates(){
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let{
                    val location = Location(latitude = it.latitude, longitude = it.longitude)
                }
            }
        }

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 1000).build()

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    fun reverseGeocodeLocation(location: Location): String{
        val geocoder = Geocoder(context, Locale.CANADA)
        val coordinate = LatLng(location.latitude, location.longitude)
        val addresses: MutableList<Address>? = geocoder.getFromLocation(coordinate.latitude, coordinate.longitude, 1)
        return if(addresses?.isNotEmpty() == true){
            addresses[0].getAddressLine(0)
        }else{
            "Address not found!"
        }
    }
}