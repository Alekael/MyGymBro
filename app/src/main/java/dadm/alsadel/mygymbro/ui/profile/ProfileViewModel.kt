package dadm.alsadel.mygymbro.ui.profile

import android.provider.ContactsContract.Data
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import dadm.alsadel.mygymbro.data.auth.AuthRepository
import dadm.alsadel.mygymbro.data.database.UserRepository
import dadm.alsadel.mygymbro.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepository: UserRepository,
                                           private val authRepository: AuthRepository): ViewModel() {

    private val _userInfo : MutableLiveData<DataSnapshot> = MutableLiveData<DataSnapshot>()

    val userInfo : LiveData<DataSnapshot>
        get() = _userInfo


    fun getUserProfile(){

        viewModelScope.launch {

            userRepository.getUserByEmail(authRepository.currentUser?.email.toString()).get().addOnSuccessListener {user ->
                if(user.exists()){
                    _userInfo.value = user.children.iterator().next()
                }
            }.addOnFailureListener {
                Log.i("ERROR", it.toString())
            }
        }
    }
}