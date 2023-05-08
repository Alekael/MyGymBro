package dadm.alsadel.mygymbro.ui.session

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import dadm.alsadel.mygymbro.R
import dadm.alsadel.mygymbro.databinding.FragmentSessionBinding

class SessionFragment : Fragment(R.layout.fragment_session) {
    private var _binding : FragmentSessionBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSessionBinding.bind(view)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}