package dadm.alsadel.mygymbro.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.databinding.FragmentStepFiveBinding
import dadm.alsadel.mygymbro.domain.model.User
import dadm.alsadel.mygymbro.ui.register.RegisterFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StepFiveFragment : Fragment(R.layout.fragment_step_five) {

    private var _binding : FragmentStepFiveBinding? = null
    private val binding get() = _binding!!
    private val viewModel : StepFiveViewModel by viewModels()

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
                val user = User(
                    RegisterFragment.RegisterFragmentCompanion.email, StepOneFragment.StepOneCompanion.textNickName,
                    StepOneFragment.StepOneCompanion.textAge, StepOneFragment.StepOneCompanion.textWeight, StepOneFragment.StepOneCompanion.textHeight,
                    StepTwoFragment.StepTwoCompanion.gender, StepThreeFragment.StepThreeCompanion.level, StepFourFragment.StepFourCompanion.objectif,
                    days, duration)

                viewModel.createUser(user,RegisterFragment.RegisterFragmentCompanion.password)

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