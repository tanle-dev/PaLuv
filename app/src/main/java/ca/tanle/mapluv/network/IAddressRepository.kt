package ca.tanle.mapluv.network

import ca.tanle.mapluv.data.remote.Response
import com.google.android.gms.maps.model.LatLng
import retrofit2.Retrofit

interface IAddressRepository {
    suspend fun getAddress(retrofit: Retrofit, latLng: String) : Response
}