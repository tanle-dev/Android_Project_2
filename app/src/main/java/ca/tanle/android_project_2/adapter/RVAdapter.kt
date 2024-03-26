package ca.tanle.android_project_2.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import ca.tanle.android_project_2.R
import ca.tanle.android_project_2.data.LocationData

class Adapter(val context: Context, val data: List<LocationData>, val rsLauncher : ActivityResultLauncher<Intent>): RecyclerView.Adapter<ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_cell, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        holder.gradeType.text = data[position].placeName
//
//        var totalGrade = 0.0
//        for (grade in data){
//            totalGrade += grade.finalPercent
//        }
//
//        holder.editBtn.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putLong("id", data[position].gradeId ?: 0)
//            bundle.putString("gradeType", data[position].gradeType ?: "")
//            bundle.putString("type", data[position].type ?: "")
//            bundle.putString("delivery", data[position].delivery ?: "")
//            bundle.putDouble("finalP", data[position].finalPercent ?: 0.0)
//            bundle.putDouble("achievedP", data[position].achievedPercent ?: 0.0)
//            bundle.putDouble("finalGradeLeft", 100 - totalGrade)
//
//            val intent = Intent(context, SecondActivity::class.java).apply {
//                putExtras(bundle)
//            }
//
//            rsLauncher.launch(intent)
//        }
    }
}

class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
//    val gradeType = view.findViewById<TextView>(R.id.gradeTypeViewText)
//    val finalPercent = view.findViewById<TextView>(R.id.finalPercentTextView)
//    val achievedPercent = view.findViewById<TextView>(R.id.achievedPercent)
//    val typeTest = view.findViewById<TextView>(R.id.typeTestTextView)
//    val editBtn = view.findViewById<Button>(R.id.editBtn)
}