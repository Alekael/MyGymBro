package dadm.alsadel.mygymbro.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import dadm.alsadel.mygymbro.MainActivity
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.databinding.FragmentProfileBinding
import dadm.alsadel.mygymbro.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel : LoginViewModel by viewModels()
    private val profileViewModel : ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        binding.btnLogout.setOnClickListener(){
            loginViewModel.logout()

            // Eliminar todas las actividades anteriores de la pila y ejecutar la actividad principal con el LoginFragment
            val intent = Intent(requireContext(),MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

            requireActivity().finish()
        }

        profileViewModel.getUserProfile()

        profileViewModel.userInfo.observe(viewLifecycleOwner){user->
            if(user != null){
                profileViewModel.getUserSessions(user.child("nickname").value.toString())

                binding.tvName.text = user.child("nickname").value.toString()
                binding.tvEmail.text = user.child("email").value.toString()
                binding.tvGender.text = user.child("gender").value.toString()
                binding.tvWeight.text = user.child("weight").value.toString() + " kg"
                binding.tvHeight.text = user.child("height").value.toString() + " cm"
                binding.tvAge.text = user.child("age").value.toString() + " years old"
                binding.tvSportLevel.text = user.child("level").value.toString()
            }
        }

        profileViewModel.userTotalSessions.observe(viewLifecycleOwner){totalSessions ->
            binding.tvTotalSessions.text = totalSessions.toString() + " sessions"
        }

        profileViewModel.userTotalHours.observe(viewLifecycleOwner){totalHours ->
            binding.tvHoursSpent.text = totalHours.toString() + " minutes"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}