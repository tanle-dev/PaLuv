package ca.tanle.paluv.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ca.tanle.paluv.data.models.Place

@Dao
interface PlaceDao {
    @Query("Delete from place_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlace(place: Place)

    @Query("Select * from `place_table`where `user_id` = :userId")
    fun getAllPlaces(userId: String): List<Place>

    @Query("Select * from `place_table` where id = :id and `user_id` = :userId")
    suspend fun getAPlace(id: String, userId: String): Place

    @Update
    suspend fun updateAPlace(place: Place)

    @Delete
    suspend fun deleteAPlace(place: Place)

    @Query("SELECT `last_updated` FROM `place_table` where `user_id` = :userId  ORDER BY `last_updated` DESC LIMIT 1")
    suspend fun getLastUpdateTimestamp(userId: String): Long?
}