package dadm.alsadel.mygymbro.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.databinding.FragmentStepFourBinding

class StepFourFragment : Fragment(R.layout.fragment_step_four) {

    private var _binding : FragmentStepFourBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStepFourBinding.bind(view)

        binding.btnContinue.setOnClickListener{
            findNavController().navigate(R.id.stepFiveFragment)
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