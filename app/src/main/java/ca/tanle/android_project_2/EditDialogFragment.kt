package ca.tanle.android_project_2

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.set
import androidx.fragment.app.DialogFragment
import ca.tanle.android_project_2.data.LocationAdapter
import ca.tanle.android_project_2.data.LocationData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditDialogFragment(context: Context, private val currentLocation: LocationData, private val locationViewModal: LocationViewModal, private val locationAdapter: LocationAdapter) : DialogFragment(), OnClickListener {

    // Text field
    lateinit var dTitle: TextView
    lateinit var tfTitle: EditText
    lateinit var tfDesc: EditText

    lateinit var tvErrorMsg: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_dialog, container, false)
        val cancelBtn = rootView.findViewById<TextView>(R.id.cancelBtn)
        val saveBtn = rootView.findViewById<TextView>(R.id.saveBtn)

        cancelBtn.setOnClickListener(this)
        saveBtn.setOnClickListener(this)

        dTitle = rootView.findViewById(R.id.dialogTitle)

        dTitle.setText("Edit Your Place")

        tfTitle = rootView.findViewById(R.id.etTitle)
        tfDesc = rootView.findViewById(R.id.etDesc)
        tvErrorMsg = rootView.findViewById(R.id.tvErrorMsg)

        tfTitle.setText(currentLocation.placeName)
        tfDesc.setText(currentLocation.placeDesc)

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
                    editPlace(currentLocation)
                    Toast.makeText(context, "Updating...", Toast.LENGTH_SHORT).show()
                    locationAdapter.notifyDataSetChanged()
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

    private fun editPlace(locationData: LocationData){
        CoroutineScope(Dispatchers.IO).launch {
            locationViewModal.updateLocation(locationData)
        }
    }

    private fun validateTextField(value: EditText): Boolean{
        val valueString = value.text.toString()
        return valueString != ""
    }
}