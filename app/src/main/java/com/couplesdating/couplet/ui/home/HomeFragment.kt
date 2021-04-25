package com.couplesdating.couplet.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentHomeBinding
import com.couplesdating.couplet.domain.model.AcceptedInvite
import com.couplesdating.couplet.domain.model.User

class HomeFragment(
    private val viewModel: HomeViewModel
) : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val navigationArgs: HomeFragmentArgs by navArgs()

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

        navigationArgs.user?.let {
            goToDashboard(it)
        } ?: run {
            viewModel.uiData.observe(viewLifecycleOwner) {
                doNavigate(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getData()
    }

    private fun doNavigate(homeUIData: HomeUIData) {
        if (homeUIData.user == null) {
            goToOnboardingOrLogin(homeUIData.acceptedInvite)
        } else {
            goToDashboard(homeUIData.user)
        }
    }

    private fun goToOnboardingOrLogin(acceptedInvite: AcceptedInvite?) {
        val direction = if (hasShownOnboarding() || acceptedInvite != null) {
            HomeFragmentDirections.actionHomeFragmentToSocialLoginFragment()
        } else {
            HomeFragmentDirections.actionHomeFragmentToOnboardingFragment()
        }
        findNavController().navigate(direction)
    }

    private fun hasShownOnboarding(): Boolean {
        val preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        return preferences.getBoolean(getString(R.string.has_shown_onboarding_key), false)
    }

    private fun goToDashboard(user: User?) {
        val toDashboard = HomeFragmentDirections.actionHomeFragmentToDashboardFragment(user)
        findNavController().navigate(toDashboard)
    }
}