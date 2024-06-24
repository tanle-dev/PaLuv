package ca.tanle.mapluv.ui.activities

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tanle.mapluv.network.IAddressRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class AddressViewModel: ViewModel() {
    private val _address = MutableLiveData<String>()
    val address: LiveData<String> = _address

    private val _id = MutableLiveData<String>()
    val id: LiveData<String> = _id

    private val _link = MutableLiveData<String>()
    val link: LiveData<String> = _link

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
}