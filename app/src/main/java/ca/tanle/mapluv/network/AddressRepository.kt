package ca.tanle.mapluv.network

import ca.tanle.mapluv.data.remote.Response
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
}