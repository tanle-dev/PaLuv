package ca.tanle.paluv.data.repositories

import ca.tanle.paluv.data.local.PlaceDao
import ca.tanle.paluv.data.models.Place
import ca.tanle.paluv.data.remote.PlaceFirebase

class PlaceRepository(private val localDao: PlaceDao, private val remote: PlaceFirebase) {

    init {
        remote.listenForFirestoreUpdates(localDao)
    }

    suspend fun addPlace(place: Place) {
        localDao.addPlace(place)
        remote.addOrUpdate(place)
    }

    fun getAllPlaces(userId: String): List<Place> {
        return localDao.getAllPlaces(userId)
    }

    suspend fun getAPlace(id: String, userId: String): Place {
        return localDao.getAPlace(id, userId)
    }

    suspend fun updateAPlace(place: Place) {
        localDao.updateAPlace(place)
        remote.updateAPlace(place)
    }

    suspend fun deleteAPlace(place: Place) {
        localDao.deleteAPlace(place)
        remote.deleteAPlace(place)
    }

    // Fetch data from Firestore and update Room
    suspend fun fetchDataFromFirestore() {
        val firestoreData = remote.getAllPlaces()
        for (document in firestoreData) {
            val roomItem = getAPlace(document.id, document.userId)
            if (roomItem == null || document.lastUpdated > roomItem.lastUpdated) {
                localDao.addPlace(document)
            }
        }
    }

    // Fetch data from Room and update Firestore
    suspend fun fetchDataFromRoom() {
        val roomData = localDao.getAllPlaces(remote.getUserId() ?: "")
        for (item in roomData) {
            val firestoreDoc = remote.getAPlace(item.id)
            if (firestoreDoc == null || item.lastUpdated > firestoreDoc.lastUpdated) {
                remote.addOrUpdate(item)
            }
        }
    }

    // Sync data between Firestore and Room
    suspend fun syncData() {
        fetchDataFromFirestore()
        fetchDataFromRoom()
    }
}