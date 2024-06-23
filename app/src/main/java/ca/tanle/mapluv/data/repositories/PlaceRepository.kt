package ca.tanle.mapluv.data.repositories

import androidx.lifecycle.LiveData
import ca.tanle.mapluv.data.local.PlaceDao
import ca.tanle.mapluv.data.models.Place
import ca.tanle.mapluv.data.models.Places
import ca.tanle.mapluv.data.remote.PlaceFirebase

class PlaceRepository(private val localDao: PlaceDao, private val remoteDao: PlaceFirebase) {
    suspend fun addPlace(place: Place) {
        localDao.addPlace(place)
    }

    fun getAllPlaces(): List<Place> {
        return localDao.getAllPlaces()
    }

    suspend fun getAPlace(id: String): Place {
        return localDao.getAPlace(id)
    }

    suspend fun updateAPlace(place: Place) {
        localDao.updateAPlace(place)
    }

    suspend fun deleteAPlace(place: Place) {
        localDao.deleteAPlace(place)
    }
}