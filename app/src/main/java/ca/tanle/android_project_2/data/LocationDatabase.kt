package ca.tanle.android_project_2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Location::class],
    version = 1,
    exportSchema = false
)
abstract class LocationDatabase: RoomDatabase() {
    abstract fun locationDao(): LocationDao

    companion object {
        private var INSTANCE: LocationDatabase? = null

        fun getDatabaseInstance(context: Context): LocationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocationDatabase::class.java,
                    "location_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}