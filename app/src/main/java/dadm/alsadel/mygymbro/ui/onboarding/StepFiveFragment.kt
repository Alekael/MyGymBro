package dadm.alsadel.mygymbro.ui.onboarding

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.data.Exercise
import dadm.alsadel.mygymbro.data.ResponseApi
import dadm.alsadel.mygymbro.data.TrainingPlan
import dadm.alsadel.mygymbro.databinding.FragmentStepFiveBinding
import dadm.alsadel.mygymbro.domain.model.User
import dadm.alsadel.mygymbro.ui.onboarding.StepOneFragment.StepOneCompanion.textNickName
import dadm.alsadel.mygymbro.ui.register.RegisterFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

@AndroidEntryPoint
class StepFiveFragment : Fragment(R.layout.fragment_step_five), ConfirmationRegisterDialog.ConfirmationDialogCallBack {

    private var _binding : FragmentStepFiveBinding? = null
    private val binding get() = _binding!!
    private val viewModel : StepFiveViewModel by viewModels()

    var days : String? = ""
    var duration : Double = 0.0

    private lateinit var reference: DatabaseReference
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val exercise:MutableList<Exercise> = mutableListOf<Exercise>()
    val muscle = "biceps"
    val api_key = "YpFI5RlK4EeVml4kF4bXrQ==ltYD4eP4o14u2kSZ"
    interface ApiService {
        @GET("exercises")
        fun getExercises(@Query("muscle") muscle: String, @Header("X-Api-Key") apiKey: String): Call<List<Exercise>>
    }


        private val service = Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    val apiService = service.create(ApiService::class.java)
    val call = apiService.getExercises(muscle, api_key)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentStepFiveBinding.bind(view)


        Log.d("TAG", "Precall")
        call.enqueue(object : Callback<List<Exercise>> {
            override fun onResponse(call: Call<List<Exercise>>, response: Response<List<Exercise>>) {
                if (response.isSuccessful) {

                    response.body()?.forEach(){
                        Log.d("TAG", "Exercies: $it")
                        if(StepThreeFragment.StepThreeCompanion.level==it.difficulty){
                            exercise.add(it)
                        }


                    }
                    response.body()
                } else {
                    println("Error: ${response.code()} ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Exercise>>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })






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
                val user = User(
                    RegisterFragment.RegisterFragmentCompanion.email, textNickName,
                    StepOneFragment.StepOneCompanion.textAge, StepOneFragment.StepOneCompanion.textWeight, StepOneFragment.StepOneCompanion.textHeight,
                    StepTwoFragment.StepTwoCompanion.gender, StepThreeFragment.StepThreeCompanion.level, StepFourFragment.StepFourCompanion.objectif,
                    days, duration)

                viewModel.createUser(user,RegisterFragment.RegisterFragmentCompanion.password)



                val plan: HashMap<String, List<Exercise>> = hashMapOf("Monday" to exercise)
                val trainingPlan = TrainingPlan(textNickName, plan)
                reference = database.getReference("TrainingPlans")
                Log.d("TAG", "plan: $plan")
                reference.child(trainingPlan.username).setValue(plan)



            }
        }

        viewModel.signUpResult.observe(viewLifecycleOwner){authResult ->
            if(authResult != null){
                viewModel.verifyEmail()
                ConfirmationRegisterDialog().show(childFragmentManager,null)

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

    override fun confirmRegister() {
        findNavController().navigate(R.id.loginFragment)
    }
}