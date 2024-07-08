package ca.tanle.mapluv.network

import ca.tanle.mapluv.data.models.DResponse
import ca.tanle.mapluv.data.models.Response
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface AddressApiService {
    @GET("maps/api/geocode/json")
    suspend fun getAddress(
        @Query("latlng") latlng: String,
        @Query("key") apiKey: String
    ): Response

    @GET("maps/api/place/textsearch/json")
    suspend fun getSearchAddress(
        @Query("query") searchText: String,
        @Query("key") apiKey: String
    ): Response

    @GET("maps/api/place/details/json")
    suspend fun getDetailPlace(
        @Query("place_id") id: String,
        @Query("key") apiKey: String
    ): DResponse

    @GET("maps/api/place/photo")
    suspend fun getPhotoPlace(
        @Query("maxwidth") width: String = "400",
        @Query("photo_reference") pRef: String,
        @Query("key") apiKey: String
    ): ResponseBody
}