package ca.tanle.mapluv.data.repositories

import ca.tanle.mapluv.data.models.User
import ca.tanle.mapluv.data.remote.PlaceFirebase

class UserRepository(private val remote: PlaceFirebase) {
    suspend fun addUser(user: User): Boolean{
        return remote.signUp(user)
    }

    suspend fun getUser(user: User): Boolean{
        return remote.signIn(user)
    }

    suspend fun logOut(): Boolean{
        return remote.logOut()
    }

    suspend fun getUserName(): Any?{
        return remote.getUserName()
    }
}