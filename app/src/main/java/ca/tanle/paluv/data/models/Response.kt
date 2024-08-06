package ca.tanle.paluv.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Response(
    val results: ArrayList<Address> = arrayListOf(),
    val status: String = ""
): Parcelable

@Parcelize
data class Address(
    val formatted_address: String,
    val geometry: Geometry,
    val name: String,
    val place_id: String
): Parcelable

@Parcelize
data class Geometry(
    val location: LocationR,
    val location_type: String
): Parcelable

@Parcelize
data class LocationR(
    val lat: Double,
    val lng: Double
): Parcelable