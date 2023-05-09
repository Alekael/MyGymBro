package dadm.alsadel.mygymbro.ui.register

import android.os.Bundle
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.data.Users
import dadm.alsadel.mygymbro.databinding.FragmentRegisterBinding
import dadm.alsadel.mygymbro.ui.register.RegisterFragment.RegisterFragmentCompanion.confirmPassword
import dadm.alsadel.mygymbro.ui.register.RegisterFragment.RegisterFragmentCompanion.email
import dadm.alsadel.mygymbro.ui.register.RegisterFragment.RegisterFragmentCompanion.password
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    object RegisterFragmentCompanion {
        var email : String = ""
        var password : String = ""
        var confirmPassword : String = ""
    }

    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterBinding.bind(view)

        binding.register.setOnClickListener(){
             email = binding.textEmail.text.toString()
             password = binding.textPassword.text.toString()
             confirmPassword = binding.textConfirmPassword.text.toString()
            if ( !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
                if (!password.equals(confirmPassword)) {
                    Snackbar.make(
                        binding.root,
                        R.string.snackDifferentPassword,
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    findNavController().navigate(R.id.stepOneFragment)
                }
            }else{
                Snackbar.make(
                    binding.root,
                    R.string.snackEmptyFields,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        binding.optionToLogin.setOnClickListener(){
            findNavController().navigate(R.id.loginFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}