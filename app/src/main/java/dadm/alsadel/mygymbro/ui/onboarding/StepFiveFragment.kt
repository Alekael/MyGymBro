package dadm.alsadel.mygymbro.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.data.Exercise
import dadm.alsadel.mygymbro.data.Users
import dadm.alsadel.mygymbro.databinding.FragmentStepFiveBinding
import dadm.alsadel.mygymbro.ui.register.RegisterFragment
import dadm.alsadel.mygymbro.data.ResponseApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


@AndroidEntryPoint
class StepFiveFragment : Fragment(R.layout.fragment_step_five) {

    private var _binding : FragmentStepFiveBinding? = null
    private val binding get() = _binding!!

    var days : String? = ""
    var duration : Double = 0.0

    private lateinit var reference: DatabaseReference
    private lateinit var database: FirebaseDatabase


    interface ExerciseService {
        @GET("/exercises")
        suspend fun getExercises(@Query("muscles")  muscle:String): ResponseApi
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStepFiveBinding.bind(view)

        val service = Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ExerciseService::class.java)

        lifecycleScope.launch {
            val Exercises:JSONArray = service.getExercises("biceps").jsonArray

            for (i in 0 until Exercises.length()) {
                val Exercise:MutableList<JSONObject> = mutableListOf<JSONObject>()

                if(StepThreeFragment.StepThreeCompanion.level==Exercises.getJSONObject(i).get("difficulty")){
                    Exercise.add( Exercises.getJSONObject(i))
                }
            }

        }

        binding.btfinish.setOnClickListener{
            if (binding.checkBoxMonday.isChecked()){
                days  += "Monday; "
            }
            if (binding.checkBoxTuesday.isChecked()){
                days  += "Tuesday; "
            }
            if (binding.checkBoxWednesday.isChecked()){
                days  += "Wednesday; "
            }
            if (binding.checkBoxThursday.isChecked()){
                days  += "Thursday; "
            }
            if (binding.checkBoxFriday.isChecked()){
                days  += "Friday; "
            }
            if (binding.checkBoxSaturday.isChecked()){
            days  += "Saturday; "
        }
            if (binding.checkBoxSunday.isChecked()){
            days  += "Sunday; "
        }
            duration = binding.txtSessionTime.text.toString().toDouble()


            if(days == "" || duration < 0.0){
                days = ""
                Snackbar.make(binding.root, R.string.snackStepFive, Snackbar.LENGTH_SHORT).show()

            }else {
                val user = Users(
                    RegisterFragment.RegisterFragmentCompanion.email, RegisterFragment.RegisterFragmentCompanion.password, StepOneFragment.StepOneCompanion.textNickName,
                    StepOneFragment.StepOneCompanion.textAge, StepOneFragment.StepOneCompanion.textWeight, StepOneFragment.StepOneCompanion.textHeight,
                    StepTwoFragment.StepTwoCompanion.gender, StepThreeFragment.StepThreeCompanion.level, StepFourFragment.StepFourCompanion.objectif,
                    days, duration)
                database = FirebaseDatabase.getInstance("https://mygymbro-ff513-default-rtdb.europe-west1.firebasedatabase.app")
                reference = database.getReference("Users")
                reference.child(StepOneFragment.StepOneCompanion.textNickName).setValue(user)
                findNavController().navigate(R.id.loginFragment)
            }
           





        }

        binding.btback.setOnClickListener(){
            findNavController().navigate(R.id.stepFourFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}