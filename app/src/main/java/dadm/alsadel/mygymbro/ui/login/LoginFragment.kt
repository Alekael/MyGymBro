package dadm.alsadel.mygymbro.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dadm.alsadel.mygymbro.HomeActivity
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login){

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!
//    private val viewModel : LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        binding.login.setOnClickListener(){

//            viewModel.loginUser(binding.email.toString(),binding.password.toString())
            val intent = Intent(activity, HomeActivity::class.java)
            startActivity(intent)
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