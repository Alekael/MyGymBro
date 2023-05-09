package dadm.alsadel.mygymbro.ui.login

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

    val _error : MutableLiveData<Exception?> = MutableLiveData<Exception?>()

    val error : LiveData<Exception?>

        get() = _error

    val _currentUser : MutableLiveData<FirebaseUser> = MutableLiveData<FirebaseUser>(authRepository.currentUser)

    val currentUser : LiveData<FirebaseUser>

    get() = _currentUser

    fun loginUser(email: String, password : String){

        viewModelScope.launch {
            authRepository.login(email, password).addOnSuccessListener {authResult ->

                _loginResult.value = authResult
                _currentUser.value = authResult.user

            }.addOnFailureListener { e ->
                    _error.value = e
                }
        }

    }

}