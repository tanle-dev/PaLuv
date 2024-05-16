package ca.tanle.mapluv.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity("place-table")
data class Place(
    @PrimaryKey() var id: String = UUID.randomUUID().toString(),
    @ColumnInfo("title") var title: String,
    @ColumnInfo("visited") var visited: Boolean,
    @ColumnInfo("lat") var lat: Double,
    @ColumnInfo("lng") var lng: Double,
    @ColumnInfo("address") var address: String,
    @ColumnInfo("comment") var comment: String,
    @ColumnInfo("rate") var rate: Int,
    @ColumnInfo("reminder-title") var reminderTitle: String,
    @ColumnInfo("reminder-date") var reminderDate: String,
    @ColumnInfo("reminder-time") var reminderTime: String,
)