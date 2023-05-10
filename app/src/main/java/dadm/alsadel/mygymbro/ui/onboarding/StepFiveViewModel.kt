package dadm.alsadel.mygymbro.ui.onboarding

import android.provider.ContactsContract.CommonDataKinds.Nickname
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import dadm.alsadel.mygymbro.data.auth.AuthRepository
import dadm.alsadel.mygymbro.data.database.UserRepository
import dadm.alsadel.mygymbro.domain.model.User
import kotlinx.coroutines.launch
import javax.inject.Inject

class StepFiveViewModel @Inject constructor(private val userRepository: UserRepository,
private val authRepository: AuthRepository): ViewModel() {

    private val _signUpResult : MutableLiveData<AuthResult?> = MutableLiveData<AuthResult?>(null)

    val signUpResult : LiveData<AuthResult?>
        get() = _signUpResult


    fun createUser(user : User,password : String){

        viewModelScope.launch {

            authRepository.signup(user.email,password).addOnSuccessListener { authResult ->



            }

        }

    }
}