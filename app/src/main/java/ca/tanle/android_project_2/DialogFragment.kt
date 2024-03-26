package ca.tanle.android_project_2

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu.OnDismissListener
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import ca.tanle.android_project_2.data.LocationData
import ca.tanle.android_project_2.data.LocationViewModal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DialogFragment(context: Context, private val currentLocation: LocationData, private val locationViewModal: LocationViewModal) : DialogFragment(), OnClickListener {

    // Text field
    lateinit var tfTitle: EditText
    lateinit var tfDesc: EditText

    lateinit var tvErrorMsg: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_dialog, container, false)
        val cancelBtn = rootView.findViewById<TextView>(R.id.cancelBtn)
        val saveBtn = rootView.findViewById<TextView>(R.id.saveBtn)
        cancelBtn.setOnClickListener(this)
        saveBtn.setOnClickListener(this)

        tfTitle = rootView.findViewById(R.id.etTitle)
        tfDesc = rootView.findViewById(R.id.etDesc)
        tvErrorMsg = rootView.findViewById(R.id.tvErrorMsg)

        tvErrorMsg.visibility = View.GONE

        return rootView
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.saveBtn -> {
                if(validateTextField(tfTitle)){
                    tvErrorMsg.visibility = View.GONE
                    currentLocation.placeName = tfTitle.text.toString()
                    currentLocation.placeDesc = tfDesc.text.toString()
                    addPlace(currentLocation)
                    Toast.makeText(context, "Saving...", Toast.LENGTH_SHORT).show()
                    this.dismiss()
                }else{
                    tvErrorMsg.visibility = View.VISIBLE
                }
            }
            R.id.cancelBtn -> {
                 this.dismiss()
            }
        }
    }

    private fun addPlace(locationData: LocationData) {
        CoroutineScope(Dispatchers.IO).launch {
            locationViewModal.addLocation(locationData)
        }
    }

    private fun validateTextField(value: EditText): Boolean{
        val valueString = value.text.toString()
        return valueString != ""
    }
}