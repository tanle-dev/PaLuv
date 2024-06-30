package ca.tanle.mapluv.ui.activities.viewmodels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tanle.mapluv.data.models.Place
import ca.tanle.mapluv.data.models.PlaceItem
import ca.tanle.mapluv.network.IAddressRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class AddressViewModel: ViewModel() {
    private val _address = MutableLiveData<String>()
    val address: LiveData<String> = _address

    private val _id = MutableLiveData<String>()
    val id: LiveData<String> = _id

    private val _link = MutableLiveData<String>()
    val link: LiveData<String> = _link

    private val _placeList = MutableLiveData<ArrayList<PlaceItem>>(arrayListOf())
    val placeList: LiveData<ArrayList<PlaceItem>> = _placeList

    fun getAddress(retrofit: Retrofit, repository: IAddressRepository, latLng: String){
        viewModelScope.launch {
            val responseData = repository.getAddress(retrofit, latLng)
            _address.value = responseData.results[0].formatted_address
            _id.value = responseData.results[0].place_id
        }
    }

    fun getPhotoLink(retrofit: Retrofit, repository: IAddressRepository, id: String){
        viewModelScope.launch(Dispatchers.IO) {
            val fetchDetail = repository.getDetailPlace(retrofit, id)
            val fetchLink = fetchDetail.result
            Log.d("Place", fetchDetail.toString())
            if (fetchLink != null && fetchLink.photos != null && fetchLink.photos.isNotEmpty()) {
                _link.postValue(fetchLink.photos[0].photo_reference)
            } else {
                // Handle the case where photos are not available
                _link.postValue("No Photo Ref")
            }
        }
    }

    private suspend fun getPlacePhoto(retrofit: Retrofit, repository: IAddressRepository, photoRef: String): Bitmap?{
        return if(photoRef.isNotEmpty()){
            try {
                val def = viewModelScope.async(Dispatchers.IO){
                    val responseBody = repository.getPhotoPlace(retrofit, photoRef)
                    Log.d("Error here", photoRef)
                    responseBody.byteStream().use {
                        BitmapFactory.decodeStream(it)
                    }
                }
                def.await()
            }catch (e: Exception){
                return null
            }
        }else null
    }

    fun getPlaceList(retrofit: Retrofit, repository: IAddressRepository, places: List<Place>){
        _placeList.value = arrayListOf()
        viewModelScope.launch {
            for (place in places){
                val photo = getPlacePhoto(retrofit, repository, place.photoLink)
                val placeItem = PlaceItem(place, photo)

                _placeList.value.let {
                    it?.add(placeItem)
                    _placeList.value = it
                }
            }
        }
    }
}