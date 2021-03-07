package com.couplesdating.couplet.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.couplesdating.couplet.R

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        doNavigate()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun doNavigate() {
        val preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val hasShownOnboarding =
            preferences.getBoolean(getString(R.string.has_shown_onboarding_key), false)

        val navController = findNavController()
        if (hasShownOnboarding) {
            val navigateToSocialLogin =
                MainFragmentDirections.actionMainFragmentToSocialLoginFragment()
            navController.navigate(navigateToSocialLogin)
        } else {
            val navigateToOnboarding =
                MainFragmentDirections.actionMainFragmentToOnboardingFragment()
            navController.navigate(navigateToOnboarding)
        }
    }
}