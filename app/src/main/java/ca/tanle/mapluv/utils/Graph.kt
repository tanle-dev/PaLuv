package ca.tanle.mapluv.utils

import android.content.Context
import androidx.room.Room
import ca.tanle.mapluv.data.local.PlaceDatabase
import ca.tanle.mapluv.data.remote.PlaceFirebase
import ca.tanle.mapluv.data.repositories.PlaceRepository

object Graph{
    lateinit var roomDatabase: PlaceDatabase
    lateinit var firebaseFirestore: PlaceFirebase

    val placeRepository by lazy {
        PlaceRepository(roomDatabase.placeDao(), firebaseFirestore)
    }

    fun provide(context: Context){
        roomDatabase = Room.databaseBuilder(context = context, PlaceDatabase::class.java, "placeList.db").build()

        firebaseFirestore = PlaceFirebase()
    }
}