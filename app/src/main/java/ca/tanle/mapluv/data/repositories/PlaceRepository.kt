package ca.tanle.mapluv.data.repositories

import ca.tanle.mapluv.data.local.PlaceDao
import ca.tanle.mapluv.data.models.Place
import ca.tanle.mapluv.data.remote.PlaceFirebase
import com.google.firebase.firestore.FirebaseFirestore

class PlaceRepository(private val localDao: PlaceDao, private val remote: PlaceFirebase) {

    suspend fun addPlace(place: Place) {
        localDao.addPlace(place)
        remote.addPlace(place)
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

    suspend fun deleteAll(){
        localDao.deleteAll()
    }
}