package ca.tanle.mapluv.ui.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tanle.mapluv.data.models.Place
import ca.tanle.mapluv.data.repositories.PlaceRepository
import ca.tanle.mapluv.utils.Graph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlacesViewModel(private val placeRepository: PlaceRepository = Graph.placeRepository): ViewModel() {

    val allPlaces: LiveData<List<Place>> = placeRepository.getAllPlaces()

    fun addNewPlace(place: Place) {
        viewModelScope.launch(Dispatchers.IO) {
            placeRepository.addPlace(place)
        }
    }
}