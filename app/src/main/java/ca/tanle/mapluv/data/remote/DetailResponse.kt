package ca.tanle.mapluv.data.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DResponse(
    val result: RResult,
    val status: String
): Parcelable

@Parcelize
data class RResult(
    val photos: ArrayList<Photo>
): Parcelable

@Parcelize
data class Photo(
    val height: Int,
    val width: Int,
    val photo_reference: String
): Parcelable