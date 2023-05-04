package dadm.alsadel.mygymbro.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.data.Users
import dadm.alsadel.mygymbro.databinding.FragmentStepFiveBinding
import dadm.alsadel.mygymbro.ui.register.RegisterFragment
import com.google.firebase.ktx.Firebase

class StepFiveFragment : Fragment(R.layout.fragment_step_five) {

    private var _binding : FragmentStepFiveBinding? = null
    private val binding get() = _binding!!

    var days : String? = ""
    var duration : Double = 0.0

    private lateinit var reference: DatabaseReference
    private lateinit var database: FirebaseDatabase


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStepFiveBinding.bind(view)

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