package ca.tanle.mapluv.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity("place_table")
data class Place(
    @PrimaryKey() var id: String = UUID.randomUUID().toString(),
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
)