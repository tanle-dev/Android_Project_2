package ca.tanle.android_project_2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import ca.tanle.android_project_2.data.LocationData
import ca.tanle.android_project_2.utils.LocationUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment(private val context: Context,private val locationViewModal: LocationViewModal) : Fragment(), OnMapClickListener, OnMapReadyCallback, OnClickListener {
    private var googleMap: GoogleMap? = null
    private var locationPermission = LocationUtils(context)

    // Default location Los Angeles
    private val defaultLocation = LatLng(34.098907, -118.327759)

    // The geographical location where the device is currently located
    private var lastKnownLocation: Location? = null

    // Button
    lateinit var saveLocationBtn: Button
    var currentLocationData: LocationData = LocationData()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)
        saveLocationBtn = rootView.findViewById(R.id.saveLocation)

//        Show map view in the fragment
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

//        Add button listener
        saveLocationBtn.setOnClickListener(this)

        return rootView
    }

    override fun onClick(v: View?) {
        if (locationPermission.hasLocationPermission(context)){
            when(v?.id){
                R.id.saveLocation -> {
                    val dialogFragment: DialogFragment = ca.tanle.android_project_2.DialogFragment(context, currentLocationData, locationViewModal)
                    dialogFragment.show(parentFragmentManager, "")
                }
            }
        }else{
            Toast.makeText(context, "Permission denied!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapClick(p0: LatLng) {
        googleMap?.clear()
        googleMap?.addMarker(
            MarkerOptions()
                .position(p0)
        )
        val cameraPosition = CameraPosition.Builder()
            .target(p0) // Sets the center of the map to Mountain View
            .zoom(17f)            // Sets the zoom
            .bearing(0f)         // Sets the orientation of the camera to east
            .tilt(30f)            // Sets the tilt of the camera to 30 degrees
            .build()              // Creates a CameraPosition from the builder
        googleMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        currentLocationData.latitude = String.format("%.6f", p0.latitude).toDouble()
        currentLocationData.longitude = String.format("%.6f", p0.longitude).toDouble()

        Toast.makeText(context, currentLocationData.latitude.toString(), Toast.LENGTH_SHORT).show()
    }

    /**
     * Implement OnMapReadyCallback to override onMapReady
     * to set up our map
     */
    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0

        updateLocationUI()
        getDeviceLocation()
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationUI(){
        if(googleMap == null){
            return
        }
        try {
            if(locationPermission.hasLocationPermission(context)){
                googleMap?.setOnMapClickListener(this)
                googleMap?.isMyLocationEnabled = true
                googleMap?.uiSettings?.isMyLocationButtonEnabled = true
                googleMap?.uiSettings?.isMapToolbarEnabled = true
                googleMap?.uiSettings?.isZoomControlsEnabled = true
                googleMap?.uiSettings?.isZoomGesturesEnabled = true
                googleMap?.uiSettings?.isCompassEnabled = true
            }else{
                googleMap?.isMyLocationEnabled = false
                googleMap?.uiSettings?.isMyLocationButtonEnabled = false
                googleMap?.uiSettings?.isMapToolbarEnabled = false
                googleMap?.uiSettings?.isZoomControlsEnabled = false
                googleMap?.uiSettings?.isZoomGesturesEnabled = false
                googleMap?.uiSettings?.isCompassEnabled = false
                lastKnownLocation = null
            }
        }catch (e: SecurityException){
            Log.e("Exception: %s", e.message, e)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation(){
        try {
            if(locationPermission.hasLocationPermission(context)){
                val locationResult = locationPermission.fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(context as Activity) {
                    task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            googleMap?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                LatLng(lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
                            googleMap?.clear()
                            currentLocationData.latitude = lastKnownLocation!!.latitude
                            currentLocationData.longitude = lastKnownLocation!!.longitude
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        googleMap?.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat()))
                        googleMap?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        }catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val DEFAULT_ZOOM = 15

        // Keys for storing activity state.
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
    }
}