package dadm.alsadel.mygymbro.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.databinding.FragmentHomeBinding
import dadm.alsadel.mygymbro.ui.session.SessionListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel : HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        homeViewModel.getUserInfo()
        homeViewModel.userInfo.observe(viewLifecycleOwner){user->
            if(user != null){
                homeViewModel.getUserSessions(user.key.toString())

                homeViewModel.userSessions.observe(viewLifecycleOwner){sessions->
                    if(sessions != null){
                        val adapter = SessionListAdapter()
                        binding.rvSessions.adapter = adapter
                        adapter.submitList(sessions)

                        binding.btnStartSession.setOnClickListener(){
                            val bundle = Bundle()
                            val sessionMap = user.value as? Map<String, Any> ?: null
                            val time = sessionMap!!["time"] as Long
                            val trainingDays = sessionMap!!["days"] as ArrayList<String>

                            bundle.putStringArrayList("trainingDays", trainingDays )
                            bundle.putInt("time", time.toInt())
                            bundle.putString("nickname", user.key)
                            bundle.putInt("sessions", sessions.size)
                            findNavController().navigate(R.id.sessionFragment, bundle)
                        }
                    }
                }

            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}