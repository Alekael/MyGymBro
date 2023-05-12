package dadm.alsadel.mygymbro.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dadm.alsadel.mygymbro.data.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _Userexist : MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    val exist : LiveData<Boolean>
        get() = _Userexist

    private val _error : MutableLiveData<Exception> = MutableLiveData<Exception>()

    val error : LiveData<Exception>
        get() = _error

    fun verifySignUp(email : String){

        viewModelScope.launch {

            authRepository.verifyUser(email).addOnSuccessListener {result ->

                if (result.signInMethods!! != null && !result.signInMethods!!.isEmpty()){
                    _Userexist.postValue(true)
                }
                else {
                    _Userexist.postValue(false)
                }
            }.addOnFailureListener {exception ->
                _error.value = exception
            }
        }


    }

}
