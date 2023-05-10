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

    private val _exist : MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    val exist : LiveData<Boolean>
        get() = _exist

    private val _error : MutableLiveData<Exception> = MutableLiveData<Exception>()

    val error : LiveData<Exception>
        get() = _error

    fun verifySignUp(email : String){

        viewModelScope.launch {
            authRepository.verifyUser(email).addOnSuccessListener {result ->
                Log.i("VERIFY",result.signInMethods.toString())
                if (result.signInMethods!! != null && !result.signInMethods!!.isEmpty()){
                    _exist.postValue(true)
                }
                else {
                    _exist.postValue(false)
                }
            }.addOnFailureListener {exception ->
                _error.value = exception
            }
        }


    }
}
