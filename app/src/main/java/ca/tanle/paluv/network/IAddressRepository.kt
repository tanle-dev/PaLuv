package ca.tanle.paluv.network

import ca.tanle.paluv.data.models.DResponse
import ca.tanle.paluv.data.models.Response
import okhttp3.ResponseBody
import retrofit2.Retrofit

interface IAddressRepository {
    suspend fun getAddress(retrofit: Retrofit, latLng: String) : Response

    suspend fun getSearchAddress(retrofit: Retrofit, searchText: String): Response

    suspend fun getDetailPlace(retrofit: Retrofit, id: String): DResponse

    suspend fun getPhotoPlace(retrofit: Retrofit, ref: String): ResponseBody
}