package ca.tanle.paluv.data.remote

import android.util.Log
import ca.tanle.paluv.data.local.PlaceDao
import ca.tanle.paluv.data.models.Place
import ca.tanle.paluv.data.models.User
import com.google.firebase.auth.auth
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PlaceFirebase {
    private val usersCollection = Firebase.firestore.collection("users")
    private val auth = Firebase.auth
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

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
            if (!uid.isNullOrEmpty()){
                usersCollection.document(uid).update(mapOf("lastLogin" to System.currentTimeMillis()))
            }
            return result.user != null
        }catch(e: Exception){
            false
        }
    }

    suspend fun signInWithGoogle(idToken: String): Boolean{

        return try {

            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)

            Log.d("AAA", firebaseCredential.toString())

            val result = auth.signInWithCredential(firebaseCredential).await()

            val uid = result.user?.uid
            val userName = result.user?.email?.split("@")?.get(0)
            val email = result.user?.uid

            if (!uid.isNullOrEmpty()){
                val data = hashMapOf<String, Any>(
                    "email" to email as Any,
                    "userName" to userName as Any,
                    "lastLogin" to System.currentTimeMillis(),
                )
                usersCollection.document(uid)
                    .set(data)
            }
            return result.user != null
        }catch (e: Exception) {
            Log.d("AAA", e.message.toString())
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

//    fun addPlace(place: Place): String?{
//        return try {
//            val uid = auth.currentUser?.uid
//            if (uid.isNullOrEmpty()) return null
//            usersCollection.document(uid).collection("places")
//                .add(place)
//                .addOnSuccessListener {
//                    Log.d("task id", it.id)
//                }
//            ""
//        }catch (e: Exception){
//            null
//        }
//    }

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

    fun getUserId():String?{
        return auth.uid
    }

    suspend fun getAllPlaces(): List<Place> {
        return try {
            usersCollection.document(auth.uid!!).collection("places").get().await().toObjects(Place::class.java)
        }catch (e: Exception){
            emptyList()
        }
    }

    suspend fun getAPlace(id: String): Place? {
        return try {
            usersCollection.document(auth.uid!!).collection("places").document(id).get().await().toObject(Place::class.java)
        }catch (e: Exception){
            null
        }
    }

    suspend fun updateAPlace(place: Place): Boolean {
        return try{
            usersCollection.document(auth.uid!!).collection("places").document(place.id).set(place).await()
            Log.d("Firestore update place", "Successfully!")
            true
        }catch (e: Exception){
            Log.d("Firestore update place", "Can not update place to Firestore")
            false
        }
    }

    suspend fun deleteAPlace(place: Place):  Boolean {
        return try{
            usersCollection.document(auth.uid!!).collection("places").document(place.id).delete().await()
            Log.d("Firestore delete place", "Successfully!")
            true
        }catch (e: Exception){
            Log.d("Firestore delete place", "Can not delete place from Firestore")
            false
        }
    }

    fun addOrUpdate(item: Place){
        try {
            usersCollection.document(auth.uid!!).collection("places").document(item.id).set(item)
        }catch (e: Exception){
            Log.d("Firestore Update", "Can not sync local data to firebase.")
        }
    }

    fun listenForFirestoreUpdates(localDao: PlaceDao) {
        try {
            usersCollection.document(auth.uid!!).collection("places")
                .addSnapshotListener { snapshots, e ->
                    Log.d("Updated", "")
                    if (e != null) {
                        return@addSnapshotListener
                    }

                    scope.launch {
                        for (dc in snapshots!!.documentChanges) {
                            val data = dc.document.toObject(Place::class.java)
                            when (dc.type) {
                                DocumentChange.Type.ADDED -> {
                                    localDao.addPlace(data)
                                }
                                DocumentChange.Type.MODIFIED -> {
                                    // Check for lastUpdate conflict
                                    val roomItem = localDao.getAPlace(data.id, auth.uid!!)
                                    if (roomItem == null || data.lastUpdated > roomItem.lastUpdated) {
                                        localDao.updateAPlace(data)
                                    }
                                }
                                DocumentChange.Type.REMOVED -> {
                                    localDao.deleteAPlace(data)
                                }
                            }
                        }
                    }
                }
        }catch (e: Exception){
            Log.d("Local Database Update", "Can not sync remote data to local db.")
        }

    }
}