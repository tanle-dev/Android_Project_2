package ca.tanle.android_project_2

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.tanle.android_project_2.data.LocationAdapter
import ca.tanle.android_project_2.data.LocationViewModal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlacesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlacesFragment(private val context: Context, private val locationViewModal: LocationViewModal) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // TODO: Database
    private lateinit var viewModel: LocationViewModal
    private lateinit var locationAdapter: LocationAdapter

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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

    private fun initRecyclerView(locations: List<ca.tanle.android_project_2.data.Location>) {
//        recyclerView.layoutManager = LinearLayoutManager(context)
    }
    private fun loadLocations() {

        CoroutineScope(Dispatchers.IO).launch {
            val locations = locationViewModal.getAllLocation()
            for (location in locations){
                println("Location: ${location.placeName}")
            }
            lifecycleScope.launch(Dispatchers.Main) {
                recyclerView = view?.findViewById(R.id.places_recycler_view)!!
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                val adapter = LocationAdapter(locations)
                recyclerView.adapter = adapter
            }
        }




//            lifecycleScope.launch(Dispatchers.Main) {
////                initRecyclerView(locations)
//                val adapter = LocationAdapter(locations)
//                if (recyclerView != null) {
//                    recyclerView.adapter = adapter
//                }
//            }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlacesFragment.
         */
        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            PlacesFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
    }
}