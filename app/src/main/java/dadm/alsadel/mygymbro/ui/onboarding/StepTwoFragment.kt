package dadm.alsadel.mygymbro.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.databinding.FragmentStepTwoBinding

class StepTwoFragment : Fragment(R.layout.fragment_step_two) {

    private var _binding : FragmentStepTwoBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStepTwoBinding.bind(view)

        binding.btcontinue.setOnClickListener{
            findNavController().navigate(R.id.stepThreeFragment)
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