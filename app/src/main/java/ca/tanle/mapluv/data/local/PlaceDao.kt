package ca.tanle.mapluv.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ca.tanle.mapluv.data.models.Place
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlace(place: Place)

    @Query("Select * from `place-table`")
    fun getAllPlaces(): LiveData<List<Place>>

    @Query("Select * from `place-table` where id = :id")
    suspend fun getAPlace(id: String): Place

    @Update
    suspend fun updateAPlace(place: Place)

    @Delete
    suspend fun deleteAPlace(place: Place)
}