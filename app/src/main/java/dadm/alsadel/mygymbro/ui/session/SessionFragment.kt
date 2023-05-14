package dadm.alsadel.mygymbro.ui.session

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.databinding.FragmentSessionBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@AndroidEntryPoint
class SessionFragment : Fragment(R.layout.fragment_session) {
    private var _binding : FragmentSessionBinding? = null
    private val binding get() = _binding!!
    private val sessionViewModel : SessionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSessionBinding.bind(view)

        val nickname = arguments?.getString("nickname")
        val totalSessions = arguments?.getInt("sessions")
        val time = arguments?.getInt("time")

        if (nickname != null) {
            sessionViewModel.getUserTrainingPlan(nickname, "Monday")
        }
        if (totalSessions !=null){
            binding.tvSessionNumber.text = "Session " + totalSessions.plus(1).toString()
        }
        if (time != null){
            createTimer(time)
        }

        sessionViewModel.actualExercise.observe(viewLifecycleOwner){exercise->
            Log.i("INFO", "Ejercicio "+ exercise.value)
            val exerciseMap = exercise.value as? Map<String, Any> ?: null
            val number = exercise.key
            val name = exerciseMap!!["name"] as? String
            val description = exerciseMap!!["instructions"] as? String
            val series = Random.nextInt(3, 5)
            val repetitions = Random.nextInt(5, 10)

            binding.tvExerciseNumber.text = number
            binding.tvDescription.text = description
            binding.tvExerciseTitle.text = name
            binding.tvReps.text = "" + series + "x" + repetitions + " reps"
        }

        binding.btnNextExercise.setOnClickListener(){
            sessionViewModel.nextExercise()
        }
    }

    fun createTimer(minutes: Int) {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        val seconds = 0

        val totalMillis = TimeUnit.HOURS.toMillis(hours.toLong()) +
                TimeUnit.MINUTES.toMillis(remainingMinutes.toLong()) +
                TimeUnit.SECONDS.toMillis(seconds.toLong())

        val timer = object : CountDownTimer(totalMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Update the UI with the remaining time
                // Convert the remaining time to minutes and seconds
                val remainingMinutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                val remainingSeconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished - TimeUnit.MINUTES.toMillis(remainingMinutes))
                // Format the remaining time as a string and update the UI
                val remainingTime = String.format("%02d:%02d", remainingMinutes, remainingSeconds)
                binding.tvRemainingTime.text = remainingTime
            }

            override fun onFinish() {
                // Do something when the timer finishes
            }
        }

        timer.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}