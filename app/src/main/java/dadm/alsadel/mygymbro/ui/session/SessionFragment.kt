package dadm.alsadel.mygymbro.ui.session

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.databinding.FragmentSessionBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.random.Random

@AndroidEntryPoint
class SessionFragment : Fragment(R.layout.fragment_session) {
    private var _binding : FragmentSessionBinding? = null
    private val binding get() = _binding!!
    private val sessionViewModel : SessionViewModel by viewModels()
    private var timer: CountDownTimer? = null;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSessionBinding.bind(view)

        val nickname = arguments?.getString("nickname")
        val sessionNumber = arguments?.getInt("sessions")?.plus(1)
        val time = arguments?.getInt("time")
        val trainingDays = arguments?.getStringArrayList("trainingDays")
        val initialTime = LocalDateTime.now()

        if (nickname != null && trainingDays != null) {
            val pickedDate = checkDay(trainingDays)
            sessionViewModel.getUserTrainingPlan(nickname, pickedDate)
        }
        if (sessionNumber !=null){
            binding.tvSessionNumber.text = "Session $sessionNumber"
        }

        if (time != null){
            createTimer(time)
        }

        sessionViewModel.actualExercise.observe(viewLifecycleOwner){exercise->
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

        binding.btnFinishSession.setOnClickListener(){
            if (nickname != null && sessionNumber != null){
                sessionViewModel.createSession(nickname, sessionNumber.toString(), initialTime)
            }
            findNavController().navigate(R.id.homeFragment)
        }
    }

    fun createTimer(minutes: Int) {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        val seconds = 0

        val totalMillis = TimeUnit.HOURS.toMillis(hours.toLong()) +
                TimeUnit.MINUTES.toMillis(remainingMinutes.toLong()) +
                TimeUnit.SECONDS.toMillis(seconds.toLong())

        timer = object : CountDownTimer(totalMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val remainingMinutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                val remainingSeconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished - TimeUnit.MINUTES.toMillis(remainingMinutes))
                val remainingTime = String.format("%02d:%02d", remainingMinutes, remainingSeconds)
                binding.tvRemainingTime.text = remainingTime
            }

            override fun onFinish() {
                // Do something when the timer finishes
            }
        }

        timer!!.start()
    }

    fun checkDay(trainingDays: ArrayList<String>): String{
        val currentDateTime = LocalDateTime.now()
        val currentDay = currentDateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)

        return if (trainingDays.contains(currentDay) ){
            currentDay
        }else{
            trainingDays.random()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
        _binding = null
    }
}