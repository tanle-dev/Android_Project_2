package ca.tanle.android_project_2

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.tanle.android_project_2.data.LocationAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [PlacesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlacesFragment(private val context: Context, private val locationViewModal: LocationViewModal) : Fragment() {
    // TODO: Database
    private lateinit var viewModel: LocationViewModal
    private lateinit var locationAdapter: LocationAdapter

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_places, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadLocations()
    }

    private fun loadLocations() {
        CoroutineScope(Dispatchers.IO).launch {
            lifecycleScope.launch(Dispatchers.Main) {
                val locations = locationViewModal.getAllLocation()
                recyclerView = view?.findViewById(R.id.places_recycler_view)!!
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                val adapter = LocationAdapter(context, locations, locationViewModal)
                adapter.notifyDataSetChanged()
                recyclerView.adapter = adapter
            }
        }
    }
}