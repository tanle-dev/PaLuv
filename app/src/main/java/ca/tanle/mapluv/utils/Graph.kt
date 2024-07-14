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
import ca.tanle.mapluv.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object Graph{
    lateinit var roomDatabase: PlaceDatabase
    lateinit var firebaseFirestore: PlaceFirebase

    val placeRepository by lazy {
        PlaceRepository(roomDatabase.placeDao(), firebaseFirestore)
    }

    val userRepository by lazy {
        UserRepository(firebaseFirestore)
    }

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Assuming 'place_table' is the table name and 'photo_link' is the column name
            database.execSQL("ALTER TABLE place_table RENAME TO place_table_tmp");

            database.execSQL("CREATE TABLE place_table (" +
                    "id TEXT NOT NULL PRIMARY KEY, " +
                    "title TEXT NOT NULL, " +
                    "visited INTEGER NOT NULL, " +
                    "lat REAL NOT NULL, " +
                    "lng REAL NOT NULL, " +
                    "address TEXT NOT NULL, " +
                    "comment TEXT NOT NULL, " +
                    "rate INTEGER NOT NULL, " +
                    "phone_number TEXT NOT NULL, " +
                    "web_link TEXT NOT NULL, " +
                    "reminder_title TEXT NOT NULL, " +
                    "reminder_date TEXT NOT NULL, " +
                    "reminder_time TEXT NOT NULL, " +
                    "photo_link TEXT NOT NULL)");  // Change from NOT NULL to nullable

            database.execSQL("INSERT INTO place_table (id, title, visited, lat, lng, address, comment, rate, phone_number, web_link, reminder_title, reminder_date, reminder_time, photo_link) " +
                    "SELECT id, title, visited, lat, lng, address, comment, rate, phone_number, web_link, reminder_title, reminder_date, reminder_time, photo_link FROM place_table_tmp");

            database.execSQL("DROP TABLE place_table_tmp");
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Assuming 'place_table' is the table name and 'photo_link' is the column name
            database.execSQL("ALTER TABLE place_table RENAME TO place_table_tmp");

            database.execSQL("CREATE TABLE place_table (" +
                    "id TEXT NOT NULL PRIMARY KEY, " +
                    "title TEXT NOT NULL, " +
                    "visited INTEGER NOT NULL, " +
                    "lat REAL NOT NULL, " +
                    "lng REAL NOT NULL, " +
                    "address TEXT NOT NULL, " +
                    "comment TEXT NOT NULL, " +
                    "rate INTEGER NOT NULL, " +
                    "phone_number TEXT NOT NULL, " +
                    "web_link TEXT NOT NULL, " +
                    "reminder_title TEXT NOT NULL, " +
                    "reminder_date TEXT NOT NULL, " +
                    "reminder_time TEXT NOT NULL, " +
                    "photo_link TEXT NOT NULL," +
                    "user_id TEXT NOT NULL, " +
                    "last_updated INTEGER NOT NULL)"
            )

//            database.execSQL("INSERT INTO place_table (id, title, visited, lat, lng, address, comment, rate, phone_number, web_link, reminder_title, reminder_date, reminder_time, photo_link) " +
//                    "SELECT id, title, visited, lat, lng, address, comment, rate, phone_number, web_link, reminder_title, reminder_date, reminder_time, photo_link FROM place_table_tmp");

            database.execSQL("DROP TABLE place_table_tmp");
        }
    }

    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Rename the existing table
            database.execSQL("ALTER TABLE place_table RENAME TO place_table_tmp")

            // Create the new table with the updated schema
            database.execSQL("""
            CREATE TABLE place_table (
                id TEXT NOT NULL,
                title TEXT NOT NULL,
                visited INTEGER NOT NULL,
                lat REAL NOT NULL,
                lng REAL NOT NULL,
                address TEXT NOT NULL,
                comment TEXT NOT NULL,
                rate INTEGER NOT NULL,
                phone_number TEXT NOT NULL,
                web_link TEXT NOT NULL,
                reminder_title TEXT NOT NULL,
                reminder_date TEXT NOT NULL,
                reminder_time TEXT NOT NULL,
                photo_link TEXT NOT NULL,
                user_id TEXT NOT NULL,
                last_updated INTEGER NOT NULL,
                PRIMARY KEY(id, user_id)
            )
        """.trimIndent())

            // Copy data from the temporary table to the new table
            database.execSQL("""
            INSERT INTO place_table (id, title, visited, lat, lng, address, comment, rate, phone_number, web_link, reminder_title, reminder_date, reminder_time, photo_link, user_id, last_updated)
            SELECT id, title, visited, lat, lng, address, comment, rate, phone_number, web_link, reminder_title, reminder_date, reminder_time, photo_link, user_id, last_updated
            FROM place_table_tmp
        """.trimIndent())

            // Drop the temporary table
            database.execSQL("DROP TABLE place_table_tmp")
        }
    }


    fun provide(context: Context){
        roomDatabase = Room.databaseBuilder(context = context, PlaceDatabase::class.java, "places.db").addMigrations(
            MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4).build()
        firebaseFirestore = PlaceFirebase()
    }
}