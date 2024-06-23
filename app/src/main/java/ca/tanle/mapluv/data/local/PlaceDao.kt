package ca.tanle.mapluv.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ca.tanle.mapluv.data.models.Place
import ca.tanle.mapluv.data.models.Places
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
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