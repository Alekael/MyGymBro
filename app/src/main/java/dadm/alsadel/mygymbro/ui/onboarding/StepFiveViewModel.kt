package dadm.alsadel.mygymbro.ui.onboarding

import android.provider.ContactsContract.CommonDataKinds.Nickname
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dadm.alsadel.mygymbro.data.auth.AuthRepository
import dadm.alsadel.mygymbro.data.database.UserRepository
import dadm.alsadel.mygymbro.domain.model.User
import kotlinx.coroutines.launch
import javax.inject.Inject

class StepFiveViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {




    fun createUser(user : User){

        viewModelScope.launch {



        }

    }
}