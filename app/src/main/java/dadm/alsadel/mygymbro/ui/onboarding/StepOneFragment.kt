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
    var textNickName : String? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStepOneBinding.bind(view)

        binding.btcontinue.setOnClickListener{
            textNickName = binding.textNickName.text.toString()
            findNavController().navigate(R.id.stepTwoFragment)
        }

        binding.btback.setOnClickListener(){
            findNavController().navigate(R.id.loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}