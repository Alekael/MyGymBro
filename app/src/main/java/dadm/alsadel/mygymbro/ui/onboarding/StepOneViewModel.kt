package dadm.alsadel.mygymbro.ui.onboarding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dadm.alsadel.mygymbro.data.database.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StepOneViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _nickNameExist : MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    val nickNameExist : LiveData<Boolean>
        get() = _nickNameExist


    /**
     * Se comprueba si el nickname que se quiere introducir existe en la base de datos de Realtime Database o no.
     * Si existe, entonces mostrará un mensaje de error
     */
    fun verifyNickName(nickName : String){

        viewModelScope.launch {
            userRepository.getUserByNickName(nickName).get().addOnSuccessListener {user ->

                _nickNameExist.value = user.value != null

            }
        }
    }


}