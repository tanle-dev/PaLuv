package ca.tanle.mapluv.data.models

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.UUID

@Entity("place_table", primaryKeys = ["id", "user_id"])
data class Place(
    @ColumnInfo() var id: String = UUID.randomUUID().toString(),
    @ColumnInfo("title") var title: String = "",
    @ColumnInfo("visited") var visited: Boolean = false,
    @ColumnInfo("lat") var lat: Double = 0.0,
    @ColumnInfo("lng") var lng: Double = 0.0,
    @ColumnInfo("address") var address: String = "",
    @ColumnInfo("comment") var comment: String = "",
    @ColumnInfo("rate") var rate: Int = 0,
    @ColumnInfo("phone_number") var phoneNumber: String = "",
    @ColumnInfo("web_link") var webLink: String = "",
    @ColumnInfo("reminder_title") var reminderTitle: String = "",
    @ColumnInfo("reminder_date") var reminderDate: String = "",
    @ColumnInfo("reminder_time") var reminderTime: String = "",
    @ColumnInfo("photo_link") var photoLink: String = "",
    @ColumnInfo("user_id") var userId: String = "",
    @ColumnInfo("last_updated") var lastUpdated: Long = 0L
): Serializable

data class PlaceItem(var place: Place, var photo: Bitmap?): Serializable