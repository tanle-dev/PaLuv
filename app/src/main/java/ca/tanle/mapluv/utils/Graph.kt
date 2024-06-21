package ca.tanle.mapluv.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ca.tanle.mapluv.data.local.PlaceDatabase
import ca.tanle.mapluv.data.remote.PlaceFirebase
import ca.tanle.mapluv.data.repositories.PlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object Graph{
    lateinit var roomDatabase: PlaceDatabase
    lateinit var firebaseFirestore: PlaceFirebase

    val placeRepository by lazy {
        PlaceRepository(roomDatabase.placeDao(), firebaseFirestore)
    }

    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        // From version 1 to version 2
        override fun migrate(db: SupportSQLiteDatabase) {
            // Remove the table
//            db.execSQL("DROP TABLE place_table") // Use the right table name

            // OR: We could update it, by using an ALTER query

            // OR: If needed, we can create the table again with the required settings
             db.execSQL("CREATE TABLE IF NOT EXISTS place_table (" +
                     "id INTEGER PRIMARY KEY," +
                     "title TEXT NOT NULL," +
                     "visited BOOLEAN," +
                     "lat REAL, " +
                     "lng REAL," +
                     "address TEXT" +
                     "comment TEXT," +
                     "rate INTEGER," +
                     "phone TEXT," +
                     "web_link TEXT," +
                     "reminder_title," +
                     "reminder_date," +
                     "reminder_time)"
             )
        }
    }

    fun provide(context: Context){
        roomDatabase = Room.databaseBuilder(context = context, PlaceDatabase::class.java, "places.db")
//            .addMigrations(MIGRATION_1_2)
            .build()
        firebaseFirestore = PlaceFirebase()
    }
}