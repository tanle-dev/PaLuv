package ca.tanle.mapluv.ui.activities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class CoordinatesViewModel: ViewModel() {
    var coordinate = MutableLiveData<LatLng>(LatLng(0.0,0.0))

    fun getCoordinate(): LatLng?{
        return coordinate.value
    }

    fun setCoordinate(latLng: LatLng){
        coordinate.value = latLng
    }
}