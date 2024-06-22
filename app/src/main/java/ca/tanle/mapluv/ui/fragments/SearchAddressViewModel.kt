package ca.tanle.mapluv.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tanle.mapluv.data.remote.Response
import ca.tanle.mapluv.network.IAddressRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class SearchAddressViewModel: ViewModel() {
    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> = _searchText

    private val _addresses = MutableLiveData<Response>()
    val addresses: LiveData<Response> = _addresses

    fun updateSearchText(text: String){
        _searchText.value = text
    }

    fun getSearchAddress(retrofit: Retrofit, repository: IAddressRepository){
        viewModelScope.launch(Dispatchers.IO) {
            val fetchData = repository.getSearchAddress(retrofit, _searchText.value!!)
            _addresses.postValue(fetchData)
        }
    }
}