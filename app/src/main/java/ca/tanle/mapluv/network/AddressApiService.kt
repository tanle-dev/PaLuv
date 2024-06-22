package ca.tanle.mapluv.network

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import ca.tanle.mapluv.data.remote.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException
import java.util.Locale

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
}