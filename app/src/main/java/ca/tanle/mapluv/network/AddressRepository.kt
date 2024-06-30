package ca.tanle.mapluv.network

import android.graphics.Bitmap
import ca.tanle.mapluv.data.remote.DResponse
import ca.tanle.mapluv.data.remote.Response
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.create

class AddressRepository: IAddressRepository {
    val API_KEY = "AIzaSyCQ585S7HpgQYcPMkkWCjBTJcboKjCqY3s"
    override suspend fun getAddress(retrofit: Retrofit, latLng: String): Response {
        val addressService = retrofit.create(AddressApiService::class.java)
        val response = addressService.getAddress(latLng, API_KEY)

        return response
    }

    override suspend fun getSearchAddress(retrofit: Retrofit, searchText: String): Response {
        val addressService = retrofit.create(AddressApiService::class.java)
        val response = addressService.getSearchAddress(searchText, API_KEY)

        return response
    }

    override suspend fun getDetailPlace(retrofit: Retrofit, id: String): DResponse {
        val addressService = retrofit.create(AddressApiService::class.java)
        val response = addressService.getDetailPlace(id, API_KEY)

        return response
    }

    override suspend fun getPhotoPlace(retrofit: Retrofit, ref: String): ResponseBody {
        val addressService = retrofit.create(AddressApiService::class.java)
        val response = addressService.getPhotoPlace(pRef = ref, apiKey = API_KEY)

        return response
    }
}