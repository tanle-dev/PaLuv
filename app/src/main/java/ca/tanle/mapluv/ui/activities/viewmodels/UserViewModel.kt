package ca.tanle.mapluv.ui.activities.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tanle.mapluv.data.models.User
import ca.tanle.mapluv.data.remote.PlaceFirebase
import ca.tanle.mapluv.data.repositories.UserRepository
import ca.tanle.mapluv.utils.Graph
import kotlinx.coroutines.launch

class UserViewModel(private val authRepository: UserRepository = Graph.userRepository): ViewModel() {
    private val _authen = MutableLiveData("login")
    val authen: LiveData<String> = _authen

    private val _userName = MutableLiveData("")
    val userName: LiveData<String> = _userName

    fun setAuthen(s: String){
        _authen.value = s
    }

    fun getUserName(){
        viewModelScope.launch{
            _userName.value = authRepository.getUserName() as String
        }
    }

    fun signIn(user: User, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = authRepository.getUser(user)
            onResult(result)
        }
    }

    fun signUp(user: User, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = authRepository.addUser(user)
            onResult(result)
        }
    }

    fun logOut(onResult: (Boolean) -> Unit){
        viewModelScope.launch {
            val rs = authRepository.logOut()
            onResult(rs)
        }
    }
}