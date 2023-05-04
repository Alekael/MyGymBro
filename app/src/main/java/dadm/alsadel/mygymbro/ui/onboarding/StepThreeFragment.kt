package dadm.alsadel.mygymbro.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.databinding.FragmentStepThreeBinding
import dadm.alsadel.mygymbro.ui.onboarding.StepThreeFragment.StepThreeCompanion.level

class StepThreeFragment : Fragment(R.layout.fragment_step_three){

    private var _binding : FragmentStepThreeBinding? = null
    private val binding get() = _binding!!
    object StepThreeCompanion{
        var level : String = ""

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStepThreeBinding.bind(view)

        binding.btnContinue.setOnClickListener{
            if (binding.radioButtonAdvanced.isChecked()){
                level  = "Advanced"
                findNavController().navigate(R.id.stepFourFragment)
            }else if(binding.radioButtonBeginner.isChecked()){
                level  = "Beginner"
                findNavController().navigate(R.id.stepFourFragment)
            }else if (binding.radioButtonIntermediate.isChecked()){
                level = "Intermediate"
                findNavController().navigate(R.id.stepFourFragment)
            } else{
                Snackbar.make(binding.root, R.string.snackSelectOption, Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.btback.setOnClickListener(){
            findNavController().navigate(R.id.stepTwoFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}