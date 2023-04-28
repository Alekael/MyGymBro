package dadm.alsadel.mygymbro.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.databinding.FragmentStepThreeBinding

class StepThreeFragment : Fragment(R.layout.fragment_step_three){

    private var _binding : FragmentStepThreeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStepThreeBinding.bind(view)

        binding.btnContinue.setOnClickListener{
            findNavController().navigate(R.id.stepFourFragment)
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