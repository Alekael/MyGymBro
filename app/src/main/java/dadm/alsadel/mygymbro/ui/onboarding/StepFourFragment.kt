package dadm.alsadel.mygymbro.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.databinding.FragmentStepFourBinding
import dadm.alsadel.mygymbro.ui.onboarding.StepFourFragment.StepFourCompanion.objectif
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StepFourFragment : Fragment(R.layout.fragment_step_four) {

    private var _binding : FragmentStepFourBinding? = null
    private val binding get() = _binding!!

    object StepFourCompanion{
        var objectif : String = ""
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStepFourBinding.bind(view)

        binding.btnContinue.setOnClickListener{
            if (binding.radioButtonWeightLoss.isChecked()){
                objectif = "Weight Loss"
                findNavController().navigate(R.id.stepFiveFragment)
            }else if(binding.radioButtonDefine.isChecked()){
                objectif = "Define muscles"
                findNavController().navigate(R.id.stepFiveFragment)
            } else if (binding.radioButtonImprove.isChecked()){
                objectif = "Improve overall fitness"
                findNavController().navigate(R.id.stepFiveFragment)
            } else{
                Snackbar.make(binding.root, R.string.snackSelectOption, Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.btback.setOnClickListener(){
            findNavController().navigate(R.id.stepThreeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}