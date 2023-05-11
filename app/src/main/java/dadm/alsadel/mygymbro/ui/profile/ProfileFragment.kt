package dadm.alsadel.mygymbro.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.data.FirebaseConnection
import dadm.alsadel.mygymbro.data.auth.AuthRepository
import dadm.alsadel.mygymbro.data.auth.AuthRepositoryImpl
import dadm.alsadel.mygymbro.data.database.UserRepository
import dadm.alsadel.mygymbro.databinding.FragmentProfileBinding
import dadm.alsadel.mygymbro.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    // TODO: Rename and change types of parameters
    //private var param1: String? = null
    //private var param2: String? = null

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val loginviewModel : LoginViewModel by viewModels()
    private val profileViewModel : ProfileViewModel by viewModels()

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }*/

    /*override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        binding.btnLogout.setOnClickListener(){
            loginviewModel.logout()
            activity?.finish()
            findNavController().navigate(R.id.loginFragment)
        }

        profileViewModel.getUserProfile()



        profileViewModel.userInfo.observe(viewLifecycleOwner){user->

            if(user != null){
                binding.tvName.text = user.child("nickname").value.toString()
                binding.tvEmail.text = user.child("email").value.toString()
                binding.tvGender.text = user.child("gender").value.toString()
                binding.tvWeight.text = user.child("weight").value.toString()
                binding.tvHeight.text = user.child("height").value.toString()
                binding.tvAge.text = user.child("age").value.toString()
                binding.tvSportLevel.text = user.child("level").value.toString()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}