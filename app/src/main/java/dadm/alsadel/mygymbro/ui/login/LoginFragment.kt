package dadm.alsadel.mygymbro.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dadm.alsadel.mygymbro.HomeActivity
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.security.auth.login.LoginException

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login){

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel : LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        binding.login.setOnClickListener(){

            viewModel.loginUser(binding.textEmail.text.toString(),binding.textPassword.text.toString())

            viewModel.loginResult.observe(viewLifecycleOwner){authResult ->
                if(authResult != null){
                    val intent = Intent(activity, HomeActivity::class.java)
                    startActivity(intent)
                }

            }
            viewModel.error.observe(viewLifecycleOwner){ error ->
                if(error != null){
                    val message = when(error){
                        is FirebaseAuthInvalidUserException -> "Usuario no existe"
                        is FirebaseAuthInvalidCredentialsException -> "Usuario y/o contraseña incorrectos"
                        else -> "ha ocurrido un error"
                    }
                    Snackbar.make(binding.root,message, Snackbar.LENGTH_SHORT).show()
                }

            }
        }




        binding.optionToRegister.setOnClickListener(){
            findNavController().navigate(R.id.registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}