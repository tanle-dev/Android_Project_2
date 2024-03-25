package ca.tanle.android_project_2.data

import android.content.Context
import androidx.room.Room

object Graph {
    lateinit var database: LocationDatabase

    val locationRepository by lazy {
        LocationRepository(database.locationDao())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context=context, LocationDatabase::class.java, "locationDb.db").build()
    }
}