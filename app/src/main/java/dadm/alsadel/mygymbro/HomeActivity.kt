package dadm.alsadel.mygymbro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dadm.alsadel.mygymbro.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = binding.navHostFragment.getFragment<NavHostFragment>().navController
        navController.navigate(R.id.homeFragment)

        // Setup bottom navigation
        binding.bottomNavigationView.setupWithNavController(navController)

    }
}