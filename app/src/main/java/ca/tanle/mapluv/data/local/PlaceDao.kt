package ca.tanle.mapluv.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ca.tanle.mapluv.data.models.Place

@Dao
interface PlaceDao {
    @Query("Delete from place_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlace(place: Place)

    @Query("Select * from `place_table`")
    fun getAllPlaces(): List<Place>

    @Query("Select * from `place_table` where id = :id")
    suspend fun getAPlace(id: String): Place

    @Update
    suspend fun updateAPlace(place: Place)

    @Delete
    suspend fun deleteAPlace(place: Place)
}