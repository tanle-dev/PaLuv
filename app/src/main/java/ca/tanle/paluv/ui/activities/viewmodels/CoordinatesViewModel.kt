package ca.tanle.paluv.ui.activities.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoordinatesViewModel @Inject constructor(): ViewModel() {
    private val _coordinate = MutableLiveData<LatLng>()
    var coordinate: LiveData<LatLng> = _coordinate

    fun setCoordinate(latLng: LatLng){
        _coordinate.value = latLng
    }
}