package dadm.alsadel.mygymbro.ui.onboarding

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.databinding.FragmentStepTwoBinding

class StepTwoFragment : Fragment(R.layout.fragment_step_two) {

    private var _binding : FragmentStepTwoBinding? = null
    private val binding get() = _binding!!
    var gender : String? = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStepTwoBinding.bind(view)

        binding.btnMale.setOnClickListener(){
            binding.btnFemale.setBackgroundColor(Color.WHITE)
            binding.btnFemale.setTextColor(Color.argb(1, 0, 86, 241))
             gender = "Male"
            binding.btnMale.setBackgroundColor(Color.argb(1, 0, 86, 241))
            binding.btnMale.setTextColor(Color.WHITE)


        }
        binding.btnFemale.setOnClickListener(){
            binding.btnMale.setBackgroundColor(Color.WHITE)
            binding.btnMale.setTextColor(Color.argb(1, 0, 86, 241))
            gender = "Female"
            binding.btnFemale.setBackgroundColor(Color.argb(1, 0, 86, 241))
            binding.btnFemale.setTextColor(Color.WHITE)

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