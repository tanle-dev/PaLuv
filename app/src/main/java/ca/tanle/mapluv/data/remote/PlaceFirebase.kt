package ca.tanle.mapluv.data.remote

import ca.tanle.mapluv.data.models.Place

class PlaceFirebase {
    suspend fun addPlace(place: Place) {

    }

    suspend fun getAllPlaces(): List<Place> {
        return listOf();
    }

    suspend fun getAPlace(id: String): Place {
        return Place("", "", false, 0.0, 0.0, "", "", 0, "", "", "");
    }

    suspend fun updateAPlace(place: Place) {

    }

    suspend fun deleteAPlace(place: Place) {

    }
}