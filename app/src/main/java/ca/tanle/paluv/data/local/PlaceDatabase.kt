package ca.tanle.paluv.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ca.tanle.paluv.data.models.Place

@Database(
    entities = [Place::class],
    version = 4,
    exportSchema = true
)
abstract class PlaceDatabase: RoomDatabase() {
    abstract fun placeDao(): PlaceDao
}
