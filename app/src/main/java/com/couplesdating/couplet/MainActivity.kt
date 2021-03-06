package com.couplesdating.couplet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpActionBarNavigation()
    }

    private fun setUpActionBarNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(this, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val shouldShowBackNavigation = shouldShowBackNavigation(destination)
            supportActionBar?.setDisplayShowHomeEnabled(shouldShowBackNavigation)
            supportActionBar?.setDisplayHomeAsUpEnabled(shouldShowBackNavigation)
        }
    }

    private fun shouldShowBackNavigation(destination: NavDestination): Boolean {
        return destination.label != "OnboardingFirstFragment" && destination.label != "SocialLoginFragment"
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment_container_view)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}