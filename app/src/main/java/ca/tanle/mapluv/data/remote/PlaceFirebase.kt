package ca.tanle.mapluv.data.remote

import android.util.Log
import ca.tanle.mapluv.data.models.Place
import ca.tanle.mapluv.data.models.User
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class PlaceFirebase {
    private val usersCollection = Firebase.firestore.collection("users")
    private val auth = Firebase.auth

    suspend fun signUp(user: User): Boolean{
        return try {
            val result = auth.createUserWithEmailAndPassword(user.email, user.pwd).await()
            val uid = result.user?.uid
            if (!uid.isNullOrEmpty()){
                val data = hashMapOf<String, Any>(
                    "email" to user.email,
                    "userName" to user.userName,
                    "lastLogin" to System.currentTimeMillis(),
                )
                usersCollection.document(uid)
                    .set(data)
            }
            return result.user != null
        }catch(e: Exception){
            false
        }
    }

    suspend fun signIn(user: User): Boolean{
        return try {
            val result = auth.signInWithEmailAndPassword(user.email, user.pwd).await()
            val uid = result.user?.uid
            Log.d("user id", uid.toString())
            if (!uid.isNullOrEmpty()){
                usersCollection.document(uid).update(mapOf("lastLogin" to System.currentTimeMillis()))
            }
            return result.user != null
        }catch(e: Exception){
            false
        }
    }

    fun logOut(): Boolean{
        return try {
            auth.signOut()
            true
        }catch (e: Exception){
            false
        }
    }

    fun addPlace(place: Place): String?{
        return try {
            val uid = auth.currentUser?.uid
            if (uid.isNullOrEmpty()) return null
            usersCollection.document(uid).collection("places")
                .add(place)
                .addOnSuccessListener {
                    Log.d("task id", it.id)
                }
            ""
        }catch (e: Exception){
            null
        }
    }

    suspend fun getUserName(): Any?{
        return try {
            val uid = auth.currentUser?.uid
            if (uid.isNullOrEmpty()) return null
            val result= usersCollection.document(uid)
                .get().await()

            result?.data?.get("userName")
        }catch (e: Exception){
            null
        }
    }

    suspend fun getAllPlaces(): List<Place> {
        return listOf();
    }

    suspend fun getAPlace(id: String): Place {
        return Place("", "", false, 0.0, 0.0, "", "", 0, "", "", "", "", "");
    }

    suspend fun updateAPlace(place: Place) {

    }

    suspend fun deleteAPlace(place: Place) {

    }
}