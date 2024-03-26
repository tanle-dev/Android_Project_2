package ca.tanle.android_project_2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import ca.tanle.android_project_2.data.LocationData
import ca.tanle.android_project_2.data.LocationViewModal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass to display the share fragment.
 */
class ShareFragment(
    private val viewModel: LocationViewModal,
    private val context: Context
) : Fragment() {

    private lateinit var shareDropDown: Spinner
    private lateinit var shareBtn: Button
    private lateinit var locations: List<LocationData>
    private lateinit var locationNames: List<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_share, container, false)
        shareDropDown = view.findViewById(R.id.share_dropdown)
        shareBtn = view.findViewById(R.id.share_btn)
        populateDropDown()
        shareBtn.setOnClickListener {
            shareLocation()
        }
        return view
    }

    private fun populateDropDown() {
        // Populate the dropdown with the list of locations
        CoroutineScope(Dispatchers.IO).launch {
            locations = viewModel.getAllLocation()
            withContext(Dispatchers.Main) {
                if(locations.isEmpty()) {
                    shareBtn.isEnabled = false
                    locationNames = listOf("No locations found")
                    val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, locationNames)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    shareDropDown.adapter = adapter
                    return@withContext
                }
                locationNames = locations.map { it.placeName }
                val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, locationNames)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                shareDropDown.adapter = adapter
            }
        }
    }

    private fun shareLocation() {
        // Share the selected location
        val selectedLocation = locations[shareDropDown.selectedItemPosition]
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Name: ${selectedLocation.placeName}\n" +
                    "Latitude: ${selectedLocation.latitude}\n" +
                    "Longitude: ${selectedLocation.longitude}\n" +
                    "Map: https://www.google.com/maps/search/?api=1&query=${selectedLocation.latitude},${selectedLocation.longitude}"
            )
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}