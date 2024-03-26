package ca.tanle.android_project_2.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ca.tanle.android_project_2.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationAdapter (private val context: Context, private val locationData: List<LocationData>, private val locationViewModal: LocationViewModal) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_cell, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locationData[position]
        
        holder.bind(location, position)
    }

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val location_place: TextView = itemView.findViewById(R.id.placeTitle)
        val location_desc: TextView = itemView.findViewById(R.id.placeDesc)
        val location_lat: TextView = itemView.findViewById(R.id.latValue)
        val location_lon: TextView = itemView.findViewById(R.id.lonValue)
        val location_details: LinearLayout = itemView.findViewById(R.id.details)
        val location_editBtn: ImageButton = itemView.findViewById(R.id.editBtn)
        val location_moreInfoBtn: ImageButton = itemView.findViewById(R.id.moreInfoBtn)
        val location_deleteBtn: ImageButton = itemView.findViewById(R.id.deleteBtn)
        var detailStatus: Boolean = false

        fun bind(locationData: LocationData, position: Int) {
            location_place.text = locationData.placeName
            location_desc.text = locationData.placeDesc
            location_lat.text = "${context.getString(R.string.lat)} ${locationData.latitude.toString()}"
            location_lon.text = "${context.getString(R.string.lon)} ${locationData.longitude.toString()}"
            location_details.visibility = View.GONE

            location_editBtn.setOnClickListener{

            }

            location_moreInfoBtn.setOnClickListener{
                detailStatus = !detailStatus
                if(detailStatus){
                    location_moreInfoBtn.setImageResource(R.drawable.ic_arrow_up_24)
                    location_details.visibility = View.VISIBLE
                }else{
                    location_moreInfoBtn.setImageResource(R.drawable.ic_arrow_down_24)
                    location_details.visibility = View.GONE
                }
            }

            location_deleteBtn.setOnClickListener{
                deletePlace(locationData)
                Toast.makeText(context, "Deleting Place...", Toast.LENGTH_SHORT).show()
                notifyItemRemoved(position)
            }
        }

        private fun deletePlace(locationData: LocationData) {
            CoroutineScope(Dispatchers.IO).launch {
                locationViewModal.deleteLocation(locationData)
            }
        }
    }

    override fun getItemCount(): Int {
        return locationData.size
    }
}