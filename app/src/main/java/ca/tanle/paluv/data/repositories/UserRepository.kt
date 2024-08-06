package ca.tanle.paluv.data.repositories

import ca.tanle.paluv.data.models.User
import ca.tanle.paluv.data.remote.PlaceFirebase


class UserRepository(private val remote: PlaceFirebase) {
    suspend fun addUser(user: User): Boolean{
        return remote.signUp(user)
    }

    suspend fun getUser(user: User): Boolean{
        return remote.signIn(user)
    }

    fun logOut(): Boolean{
        return remote.logOut()
    }

    suspend fun getUserName(): Any?{
        return remote.getUserName()
    }

    fun getUserId(): String?{
        return remote.getUserId()
    }

    suspend fun signInWithGoogle(idToken: String): Boolean{
        return remote.signInWithGoogle(idToken)
    }
}