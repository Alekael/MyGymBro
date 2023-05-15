package dadm.alsadel.mygymbro.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import dadm.alsadel.mygymbro.domain.model.Session
import dadm.alsadel.mygymbro.data.auth.AuthRepository
import dadm.alsadel.mygymbro.data.database.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(private val userRepository: UserRepository,
                                         private val authRepository: AuthRepository): ViewModel() {

    private val _userInfo : MutableLiveData<DataSnapshot> = MutableLiveData<DataSnapshot>()
    private val _userSessions : MutableLiveData<MutableList<Session>> = MutableLiveData<MutableList<Session>>(mutableListOf())

    val userInfo : LiveData<DataSnapshot>
        get() = _userInfo
    val userSessions : MutableLiveData<MutableList<Session>>
        get() = _userSessions

    /**
     * Obtiene toda la información del usuario que está logueado en ese momento
     */
    fun getUserInfo(){
        viewModelScope.launch {
            userRepository.getUserByEmail(authRepository.currentUser?.email.toString()).get().addOnSuccessListener {user ->
                if(user.exists()){
                    _userInfo.value = user.children.iterator().next()

                }
            }
        }
    }

    /**
     * Obtiene todas las sesiones realizadas por el usuario logueado
     */
    fun getUserSessions(nickname: String){
        viewModelScope.launch {
            userRepository.getUserSessionsByNickname(nickname).get().addOnSuccessListener {sessions ->
                if(sessions.exists()){
                    val it = sessions.children.iterator()
                    val sessionsList = mutableListOf<Session>()

                    while (it.hasNext()) {
                        val snapshot = it.next() as DataSnapshot
                        val session = dataSnapshotToSession(snapshot)
                        if (session != null) {
                            sessionsList.add(session)
                        }
                    }
                    _userSessions.postValue(sessionsList)
                }

            }
        }
    }

    /**
     * Convierte el objeto DataSnapshot de una sesión al objeto Session
     */
    private fun dataSnapshotToSession(dataSnapshot: DataSnapshot): Session? {
        val sessionMap = dataSnapshot.value as? Map<String, Any> ?: return null
        val hour = sessionMap["hour"] as? String
        val day = sessionMap["day"] as? String
        val totalTime = sessionMap["totalTime"] as? Long

        return Session(dataSnapshot.key, totalTime!!.toInt(), hour, day)
    }



}