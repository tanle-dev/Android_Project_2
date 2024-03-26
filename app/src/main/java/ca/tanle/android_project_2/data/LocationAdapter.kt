package ca.tanle.android_project_2.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.tanle.android_project_2.R

class LocationAdapter (private val Locations: List<Location>) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.location_layout, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = Locations[position]
        
        holder.bind(location)
    }

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val location_place: TextView = itemView.findViewById(R.id.location_place)
        val location_desc: TextView = itemView.findViewById(R.id.location_desc)

        fun bind(location: Location) {
            location_place.text = location.placeName
            location_desc.text = location.placeDesc
        }
    }

    override fun getItemCount(): Int {
        return Locations.size
    }
}