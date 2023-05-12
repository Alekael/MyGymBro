package dadm.alsadel.mygymbro.ui.onboarding

import android.provider.ContactsContract.CommonDataKinds.Nickname
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import dadm.alsadel.mygymbro.data.auth.AuthRepository
import dadm.alsadel.mygymbro.data.database.UserRepository
import dadm.alsadel.mygymbro.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StepFiveViewModel @Inject constructor(private val userRepository: UserRepository,
private val authRepository: AuthRepository): ViewModel() {

    private val _signUpResult : MutableLiveData<AuthResult?> = MutableLiveData<AuthResult?>(null)

    val signUpResult : LiveData<AuthResult?>
        get() = _signUpResult

    private val _errorSignUp : MutableLiveData<Exception> = MutableLiveData<java.lang.Exception>()

    val errorSignUp : LiveData<Exception>
        get() = _errorSignUp

    private val _realEmail : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    val realEmail : LiveData<Boolean>
        get() = _realEmail

    private val _errorVerificationEmail : MutableLiveData<Exception> = MutableLiveData<Exception>()

    val errorVerificationEmail : LiveData<Exception>
        get() = _errorVerificationEmail

    fun createUser(user : User,password : String){

        viewModelScope.launch {

                authRepository.signup(user.email,password).addOnSuccessListener { authResult ->

                    _signUpResult.value = authResult

                }.addOnFailureListener {exception ->

                    _errorSignUp.value = exception
                }
                userRepository.addUser(user)
            }
        }
    fun verifyEmail(){

        viewModelScope.launch {

            authRepository.verifyEmail().addOnSuccessListener {

                _realEmail.value = true

            }.addOnFailureListener {exception ->

                _realEmail.value = false
                _errorVerificationEmail.value = exception
            }
        }
    }
}