package ca.tanle.android_project_2

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
import androidx.fragment.app.Fragment
import ca.tanle.android_project_2.data.LocationViewModal
import ca.tanle.android_project_2.utils.LocationUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapFragment(private val context: Context,private val locationViewModal: LocationViewModal) : Fragment(), OnMapClickListener, OnMapReadyCallback, OnClickListener {
    private var googleMap: GoogleMap? = null
    private var locationPermission = LocationUtils(context)

//    TODO: Database

    // Default location Los Angeles
    private val defaultLocation = LatLng(34.098907, -118.327759)

    // The geographical location where the device is currently located
    private var lastKnownLocation: Location? = null

    // Button
    private lateinit var currentLocationBtn: Button
    private lateinit var saveLocationBtn: Button

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.currentLocation -> {
                getDeviceLocation()
            }

            R.id.saveLocation -> {

                lastKnownLocation?.let {
                    val location = ca.tanle.android_project_2.data.Location(
                        placeName = "Your location",
                        placeDesc = "Your current location",
                        latitude = it.latitude,
                        longitude = it.longitude
                    )

                    this.addPlace(location)
                }

            }
        }
    }

    private fun addPlace(location: ca.tanle.android_project_2.data.Location) {
        CoroutineScope(Dispatchers.IO).launch {
            locationViewModal.addLocation(location)
            Log.i("MapFragment", "Location added")

            val locations = locationViewModal.getAllLocation()
//            for (location in locations){
//                Log.i("MapFragment", "Location: ${location.placeName}")
//            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)
        currentLocationBtn = rootView.findViewById(R.id.currentLocation)
        saveLocationBtn = rootView.findViewById(R.id.saveLocation)

//        TODO: DatabaseFun



        val mapView = rootView.findViewById<MapView>(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

//        Add button listener
        currentLocationBtn.setOnClickListener(this)
        saveLocationBtn.setOnClickListener(this)

        return rootView
    }

    override fun onMapClick(p0: LatLng) {
        googleMap?.clear()
        googleMap?.addMarker(
            MarkerOptions()
                .position(p0)
        )
        Log.d("MapFragment", "onMapClick: $p0")
    }

    /**
     * Saves the state of the map when the activity is paused.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        googleMap?.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(KEY_LOCATION, lastKnownLocation)
        }
        super.onSaveInstanceState(outState)
    }

    /**
     * Implement OnMapReadyCallback to override onMapReady
     * to set up our map
     */
    override fun onMapReady(p0: GoogleMap) {
        this.googleMap = p0

        getDeviceLocation()
        updateLocationUI()
        googleMap?.setOnMapClickListener(this)
    }

    private fun updateLocationUI() {
        if (googleMap == null) {
            return
        }
        try {
            if (locationPermission.hasLocationPermission(context)) {
                googleMap?.isMyLocationEnabled = true
                googleMap?.uiSettings?.isMyLocationButtonEnabled = true
                googleMap?.uiSettings?.isMapToolbarEnabled = true
                googleMap?.uiSettings?.isZoomControlsEnabled = true
                googleMap?.uiSettings?.isCompassEnabled = true
            } else {
                googleMap?.isMyLocationEnabled = false
                googleMap?.uiSettings?.isMyLocationButtonEnabled = false
                googleMap?.uiSettings?.isMapToolbarEnabled = false
                googleMap?.uiSettings?.isZoomControlsEnabled = false
                googleMap?.uiSettings?.isCompassEnabled = false
                lastKnownLocation = null
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun getDeviceLocation() {
        try {
            if (locationPermission.hasLocationPermission(context)) {
                val locationResult = locationPermission.fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(context as Activity) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            googleMap?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude
                                    ), DEFAULT_ZOOM.toFloat()
                                )
                            )
                            googleMap?.clear()
                            googleMap?.addMarker(
                                MarkerOptions()
                                    .title("Your location")
                                    .draggable(true)
                                    .position(
                                        LatLng(
                                            lastKnownLocation!!.latitude,
                                            lastKnownLocation!!.longitude
                                        )
                                    )
                            )
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        googleMap?.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                        )
                        googleMap?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
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