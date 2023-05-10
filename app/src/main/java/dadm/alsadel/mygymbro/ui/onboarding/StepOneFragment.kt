package dadm.alsadel.mygymbro.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.databinding.FragmentStepOneBinding
import dadm.alsadel.mygymbro.ui.onboarding.StepOneFragment.StepOneCompanion.textAge
import dadm.alsadel.mygymbro.ui.onboarding.StepOneFragment.StepOneCompanion.textHeight
import dadm.alsadel.mygymbro.ui.onboarding.StepOneFragment.StepOneCompanion.textNickName
import dadm.alsadel.mygymbro.ui.onboarding.StepOneFragment.StepOneCompanion.textWeight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StepOneFragment : Fragment(R.layout.fragment_step_one) {

    private var _binding : FragmentStepOneBinding? = null
    private val binding get() = _binding!!
    private val viewModel : StepOneViewModel by viewModels()

    object StepOneCompanion{
        var textNickName : String = ""
        var textAge : Int = 0
        var textWeight : Double = 0.0
        var textHeight : Double = 0.0
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStepOneBinding.bind(view)

        binding.btcontinue.setOnClickListener{

            textNickName = binding.textNickName.text.toString()
            textAge = binding.textAge.text.toString().toInt()
            textWeight  = binding.textWeight.text.toString().toDouble()
            textHeight  = binding.textHeight.text.toString().toDouble()

            if(textNickName.isNullOrEmpty() || textAge == 0 || textWeight < 0.0 || textHeight < 0.0) {
                Snackbar.make(binding.root, R.string.snackStepOne, Snackbar.LENGTH_SHORT).show()

            }
            else {
                viewModel.verifyNickName(textNickName)
            }
        }

        viewModel.nickNameExist.observe(viewLifecycleOwner){
            if(it){
                Snackbar.make(binding.root,"El nickName existe",Snackbar.LENGTH_SHORT).show()
            }
            else {
                findNavController().navigate(R.id.stepTwoFragment)
            }
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