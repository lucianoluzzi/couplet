package com.couplesdating.couplet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
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
    private val toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpActionBarNavigation()
        intent?.getStringExtra("pair_info")?.let {
            navHostFragment.findNavController().navigate(
                R.id.partnerAcceptedInviteFragment, bundleOf(
                    "pair_info" to it
                )
            )
        }
    }

    private fun setUpActionBarNavigation() {
        setSupportActionBar(toolbar)
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            viewModel.trackScreenShown(destination.label.toString())
            val shouldShowBackNavigation = shouldShowActionBar(destination)
            with(toolbar) {
                navigationIcon = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_back)
                title = ""
                isVisible = shouldShowBackNavigation
            }
        }
    }

    private fun shouldShowActionBar(destination: NavDestination): Boolean {
        val label = destination.label ?: return true
        return !label.contains("Onboarding")
                && label != "SocialLoginFragment"
                && label != "DashboardFragment"
                && label != "InvitedFragment"
                && label != "ProfileFragment"
    }
}