package ca.tanle.mapluv.ui.activities

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tanle.mapluv.network.IAddressRepository
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class AddressViewModel: ViewModel() {
    private val _address = MutableLiveData<String>()
    val address: LiveData<String> = _address

    fun getAddress(retrofit: Retrofit, repository: IAddressRepository, latLng: String){
        viewModelScope.launch {
            val responseData = repository.getAddress(retrofit, latLng)
            _address.value = responseData.results[0].formatted_address
        }
    }
}