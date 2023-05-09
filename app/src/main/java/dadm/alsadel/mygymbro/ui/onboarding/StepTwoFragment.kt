package dadm.alsadel.mygymbro.ui.onboarding

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.google.android.material.snackbar.Snackbar
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.databinding.FragmentStepTwoBinding
import dadm.alsadel.mygymbro.ui.onboarding.StepTwoFragment.StepTwoCompanion.gender
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StepTwoFragment : Fragment(R.layout.fragment_step_two) {

    private var _binding : FragmentStepTwoBinding? = null
    private val binding get() = _binding!!

    object StepTwoCompanion{
        var gender : String? = ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStepTwoBinding.bind(view)

        binding.btnMale.setOnClickListener(){

             gender = "Male"



        }
        binding.btnFemale.setOnClickListener(){

            gender = "Female"
           

        }

        binding.btcontinue.setOnClickListener{
            if (gender == ""){
                Snackbar.make(binding.root, R.string.snackStepTwo, Snackbar.LENGTH_SHORT).show()
            }
            else {
                findNavController().navigate(R.id.stepThreeFragment)
            }
        }

        binding.btback.setOnClickListener(){
            findNavController().navigate(R.id.stepOneFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}