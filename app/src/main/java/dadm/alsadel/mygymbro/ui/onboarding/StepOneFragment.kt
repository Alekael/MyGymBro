package dadm.alsadel.mygymbro.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.databinding.FragmentStepOneBinding

class StepOneFragment : Fragment(R.layout.fragment_step_one) {

    private var _binding : FragmentStepOneBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStepOneBinding.bind(view)

        binding.btcontinue.setOnClickListener{
            navigateToStepTwo()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToStepTwo(){
        findNavController().navigate(R.id.stepTwoFragment)
    }

}