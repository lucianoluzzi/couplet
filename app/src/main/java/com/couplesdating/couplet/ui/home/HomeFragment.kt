package com.couplesdating.couplet.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentHomeBinding
import com.couplesdating.couplet.domain.model.User
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var binding: FragmentHomeBinding
    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            doNavigate(user)
        }
    }

    private fun doNavigate(user: User?) {
        if (user == null) {
            goToOnboardingOrLogin()
        } else {
            goToDashboard()
        }
    }

    private fun goToOnboardingOrLogin() {
        val direction = if (hasShownOnboarding()) {
            HomeFragmentDirections.actionHomeFragmentToSocialLoginFragment()
        } else {
            HomeFragmentDirections.actionHomeFragmentToOnboardingFragment()
        }
        navController.navigate(direction)
    }

    private fun hasShownOnboarding(): Boolean {
        val preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        return preferences.getBoolean(getString(R.string.has_shown_onboarding_key), false)
    }

    private fun goToDashboard() {
        val toDashboard = HomeFragmentDirections.actionHomeFragmentToDashboardFragment()
        findNavController().navigate(toDashboard)
    }
}