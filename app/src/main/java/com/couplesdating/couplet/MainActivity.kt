package com.couplesdating.couplet

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp
import com.couplesdating.couplet.ui.MainViewModel
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinExperimentalAPI

@KoinExperimentalAPI
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
    }
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpActionBarNavigation()
    }

    private fun setUpActionBarNavigation() {
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(this, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            viewModel.trackScreenShown(destination.label.toString())

            val shouldShowBackNavigation = shouldShowActionBar(destination)
            if (shouldShowBackNavigation) {
                supportActionBar?.show()
            } else {
                supportActionBar?.hide()
            }
        }
    }

    private fun shouldShowActionBar(destination: NavDestination): Boolean {
        val label = destination.label ?: return true
        return !label.contains("Onboarding")
                && label != "SocialLoginFragment"
                && label != "DashboardFragment"
                && label != "InvitedFragment"
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment_container_view)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}