package dadm.alsadel.mygymbro.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import dadm.alsadel.mygymbro.data.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<AuthResult?>(null)

    val loginResult : LiveData<AuthResult?>

        get() = _loginResult

    private val _error : MutableLiveData<Exception?> = MutableLiveData<Exception?>()

    private val _emailVerified : MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    val emailVerified : LiveData<Boolean>
        get() = _emailVerified

    val error : LiveData<Exception?>

        get() = _error

    fun loginUser(email: String, password : String){

        viewModelScope.launch {
            authRepository.login(email, password).addOnSuccessListener {

                _emailVerified.value = authRepository.currentUser!!.isEmailVerified


            }.addOnFailureListener { e ->
                    _error.value = e
                }
        }

    }

    fun logout(){
        authRepository.logout()
        _loginResult.value = null
    }

}