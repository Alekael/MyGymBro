package dadm.alsadel.mygymbro.ui.session

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import dadm.alsadel.mygymbro.domain.model.Session
import dadm.alsadel.mygymbro.data.database.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SessionViewModel  @Inject constructor(private val userRepository: UserRepository): ViewModel() {
    private val _trainingPlan : MutableLiveData<DataSnapshot> = MutableLiveData<DataSnapshot>()
    private val _actualExercise : MutableLiveData<DataSnapshot> = MutableLiveData<DataSnapshot>()

    val trainingPlan : LiveData<DataSnapshot>
        get() = _trainingPlan
    val actualExercise : LiveData<DataSnapshot>
        get() = _actualExercise

    /**
     * Obtiene el plan de entrenamiento del usuario que está logueado
     */
    fun getUserTrainingPlan(nickname: String, dayOfWeek: String){
        viewModelScope.launch {
            userRepository.getUserTrainingByNickname(nickname, dayOfWeek).get().addOnSuccessListener {trainingPlan ->
                if(trainingPlan.exists()){
                    _trainingPlan.value = trainingPlan
                    _actualExercise.value = trainingPlan.children.iterator().next()
                    Log.i("INFO", "TrainingPlan: " + _trainingPlan.value.toString())
                    Log.i("INFO", "Actual exercise: " + _actualExercise.value.toString())
                }
            }.addOnFailureListener {
                Log.i("ERROR", it.toString())
            }
        }
    }

    /**
     * Obtiene el siguiente ejercicio de la sesión y mostrará en la pantalla
     */
    fun nextExercise() {
        _trainingPlan.value?.let { plan ->
            val iterator = plan.children.iterator()
            while (iterator.hasNext()) {
                val nextExercise = iterator.next()
                if (nextExercise.key == _actualExercise.value?.key && iterator.hasNext()) {
                    _actualExercise.value = iterator.next()
                    break
                }
            }
        }
    }

    /**
     * Crea una nueva sesión y se añade esa sesión a la base de datos
     */
    fun createSession(nickname: String, number: String, initialTime: LocalDateTime){
        viewModelScope.launch {
            val currentDateTime = LocalDateTime.now()
            val dayOfWeek = currentDateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val formattedDateTime = currentDateTime.format(formatter)
            val duration = getMinutesBetweenDates(initialTime, LocalDateTime.now())

            var session = Session(number, duration, formattedDateTime, dayOfWeek)
            userRepository.addUserSession(nickname, number, session)
        }
    }

    /**
     * Obtiene los minutos de la sesión que ha realizado el usuario
     */
    fun getMinutesBetweenDates(start: LocalDateTime, end: LocalDateTime): Int {
        val duration: Duration = Duration.between(start, end)
        return duration.toMinutes().toInt()
    }

}