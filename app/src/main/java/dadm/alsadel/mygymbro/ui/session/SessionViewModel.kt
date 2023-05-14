package dadm.alsadel.mygymbro.ui.session

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import dadm.alsadel.mygymbro.data.Exercise
import dadm.alsadel.mygymbro.data.Session
import dadm.alsadel.mygymbro.data.auth.AuthRepository
import dadm.alsadel.mygymbro.data.database.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SessionViewModel  @Inject constructor(private val userRepository: UserRepository): ViewModel() {
    private val _trainingPlan : MutableLiveData<DataSnapshot> = MutableLiveData<DataSnapshot>()
    private val _actualExercise : MutableLiveData<DataSnapshot> = MutableLiveData<DataSnapshot>()

    val trainingPlan : LiveData<DataSnapshot>
        get() = _trainingPlan
    val actualExercise : LiveData<DataSnapshot>
        get() = _actualExercise

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

}