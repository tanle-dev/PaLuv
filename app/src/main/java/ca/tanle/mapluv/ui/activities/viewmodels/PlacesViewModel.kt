package ca.tanle.mapluv.ui.activities.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tanle.mapluv.data.models.Place
import ca.tanle.mapluv.data.repositories.PlaceRepository
import ca.tanle.mapluv.utils.Graph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlacesViewModel: ViewModel() {

    private val _places = MutableLiveData<List<Place>>()
    val places: LiveData<List<Place>> = _places

    private val _place = MutableLiveData(Place())
    val place: LiveData<Place> = _place

    fun getAllPlaces(placeRepository: PlaceRepository = Graph.placeRepository){
        _places.postValue(emptyList())
        viewModelScope.launch(Dispatchers.IO) {
            val fetchData = placeRepository.getAllPlaces()
            _places.postValue(fetchData)
        }
    }

    fun addNewPlace(place: Place, placeRepository: PlaceRepository = Graph.placeRepository) {
        viewModelScope.launch(Dispatchers.IO) {
            placeRepository.addPlace(place)
//            placeRepository.deleteAll()
        }
    }

    fun deletePlace(place: Place, placeRepository: PlaceRepository = Graph.placeRepository){
        viewModelScope.launch(Dispatchers.IO) {
            placeRepository.deleteAPlace(place)
            getAllPlaces()
        }
    }

    fun updatePlace(place: Place, placeRepository: PlaceRepository = Graph.placeRepository){
        viewModelScope.launch(Dispatchers.IO){
            placeRepository.updateAPlace(place)
            getAllPlaces()
        }
    }

    fun setPlace(place: Place){
        _place.value = place
    }

    fun addPlaceId(id: String){
        place.value?.id = id
    }

    fun addPlaceName(name: String){
        place.value?.title = name
    }

    fun addPlaceAddress(address: String){
        place.value?.address = address
    }

    fun addLatLng(lat: Double, lng: Double){
        place.value?.lat = lat
        place.value?.lng = lng
    }

    fun isVisited(state: Boolean){
        place.value?.visited = state
    }

    fun ratingPlace(rate: Int){
        place.value?.rate = rate
    }

    fun addComment(comment: String){
        place.value?.comment = comment
    }

    fun addPhoneNumber(number: String){
        place.value?.phoneNumber = number
    }

    fun addWebLink(link: String){
        place.value?.webLink = link
    }

    fun addReTitle(title: String){
        place.value?.reminderTitle = title
    }

    fun addReDate(date: String){
        place.value?.reminderDate = date
    }

    fun addReTime(time: String){
        place.value?.reminderTime = time
    }

    fun addPhotoLink(link: String){
        place.value?.photoLink = link
    }
}