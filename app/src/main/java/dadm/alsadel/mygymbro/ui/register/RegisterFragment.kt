package dadm.alsadel.mygymbro.ui.register

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import dadm.alsadel.mygymbro.R
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
    private val viewModel : RegisterViewModel by viewModels()


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

                    viewModel.verifySignUp(email)
                }
            }else{
                Snackbar.make(
                    binding.root,
                    R.string.snackEmptyFields,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.exist.observe(viewLifecycleOwner){exist ->
            if(exist){
                Snackbar.make(binding.root,
                    "This email address is already in use. Please try logging in or resetting your password.",
                    Snackbar.LENGTH_SHORT).show()
            }
            else {
                findNavController().navigate(R.id.stepOneFragment)
            }
        }

        viewModel.error.observe(viewLifecycleOwner){error ->
            if(error != null){
                val message = when(error){
                    is FirebaseNetworkException -> "Unable to connect to verify user. Please check your internet connection and try again."
                    else -> "A Firebase error has ocurred. Please try again later or contact support for assistance."
                }

                Snackbar.make(binding.root,message, Snackbar.LENGTH_SHORT).show()
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