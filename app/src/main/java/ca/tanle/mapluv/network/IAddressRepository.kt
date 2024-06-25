package ca.tanle.mapluv.network

import android.graphics.Bitmap
import ca.tanle.mapluv.data.remote.DResponse
import ca.tanle.mapluv.data.remote.Response
import com.google.android.gms.maps.model.LatLng
import okhttp3.ResponseBody
import retrofit2.Retrofit

interface IAddressRepository {
    suspend fun getAddress(retrofit: Retrofit, latLng: String) : Response

    suspend fun getSearchAddress(retrofit: Retrofit, searchText: String): Response

    suspend fun getDetailPlace(retrofit: Retrofit, id: String): DResponse

    suspend fun getPhotoPlace(retrofit: Retrofit, ref: String): ResponseBody
}