package ca.tanle.android_project_2.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.core.content.ContextCompat
import ca.tanle.android_project_2.data.LocationData
import com.google.android.gms.location.LocationServices
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

    fun reverseGeocodeLocation(location: LocationData): String{
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