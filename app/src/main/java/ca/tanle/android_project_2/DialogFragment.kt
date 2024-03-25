package ca.tanle.android_project_2

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.PopupMenu.OnDismissListener
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class DialogFragment(context: Context) : DialogFragment(), OnClickListener {

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
        cancelBtn.setOnClickListener(this)

        return rootView
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.saveBtn -> {

            }
            R.id.cancelBtn -> {
                 this.dismiss()
            }
        }
    }
}