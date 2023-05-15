package dadm.alsadel.mygymbro.ui.profile

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
class ProfileViewModel @Inject constructor(private val userRepository: UserRepository,
                                           private val authRepository: AuthRepository): ViewModel() {

    private val _userInfo : MutableLiveData<DataSnapshot> = MutableLiveData<DataSnapshot>()
    private var _userTotalSessions : MutableLiveData<Int> = MutableLiveData<Int>()
    private val _userTotalHours : MutableLiveData<Float> = MutableLiveData<Float>()

    val userInfo : LiveData<DataSnapshot>
        get() = _userInfo
    val userTotalSessions : MutableLiveData<Int>
        get() = _userTotalSessions
    val userTotalHours : MutableLiveData<Float>
        get() = _userTotalHours

    /**
     * Obtiene toda la información del usuario que está logueado en ese momento
     */
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

    /**
     * Obtiene todas las sesiones realizadas por el usuario logueado en ese momento y actualiza en la parte de progress
     */
    fun getUserSessions(nickname: String){
        viewModelScope.launch {
            userRepository.getUserSessionsByNickname(nickname).get().addOnSuccessListener {sessions ->
                if(sessions.exists()){
                    val it = sessions.children.iterator()
                    var countSessions = 0;
                    var countHours = 0.0;

                    while (it.hasNext()) {
                        val snapshot = it.next() as DataSnapshot
                        countSessions += 1;
                        val session = dataSnapshotToSession(snapshot)
                        if (session != null) {
                            Log.i("INFO", "session " + session)
                            countHours += session.totalTime!!
                        }
                    }
                    _userTotalSessions.value = countSessions
                    _userTotalHours.value = countHours.toFloat()
                }

            }.addOnFailureListener {
                Log.i("ERROR", it.toString())
            }
        }
    }

    /**
     * Convierte una sesión que es dataSnapshot a un objeto Session
     */
    private fun dataSnapshotToSession(dataSnapshot: DataSnapshot): Session? {
        val sessionMap = dataSnapshot.value as? Map<String, Any> ?: return null
        val hour = sessionMap["hour"] as? String
        val day = sessionMap["day"] as? String
        val time = sessionMap["totalTime"] as? Long

        return Session(dataSnapshot.key, time!!.toInt(), hour, day)
    }
}