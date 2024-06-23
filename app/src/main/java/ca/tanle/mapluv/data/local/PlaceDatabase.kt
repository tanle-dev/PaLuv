package ca.tanle.mapluv.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ca.tanle.mapluv.data.models.Place

@Database(
    entities = [Place::class],
    version = 1,
    exportSchema = true
)
abstract class PlaceDatabase: RoomDatabase() {
    abstract fun placeDao(): PlaceDao
}
