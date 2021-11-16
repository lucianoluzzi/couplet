package com.couplesdating.couplet.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.couplesdating.couplet.databinding.FragmentHomeBinding
import com.couplesdating.couplet.domain.model.InviteModel
import com.couplesdating.couplet.domain.model.User

class HomeFragment(
    private val viewModel: HomeViewModel
) : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val navigationArgs: HomeFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HOME_FRAGMENT", "onCreate")
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
        navigationArgs.user?.let {
            Log.d("HOME_FRAGMENT", "onViewCreated user received from navArgs")
            goToDashboard(it)
        } ?: run {
            viewModel.uiData.observe(viewLifecycleOwner) {
                Log.d("HOME_FRAGMENT", "navigating from liveData")
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
            goToOnboardingOrLogin(homeUIData.invite)
        } else {
            goToDashboard(homeUIData.user)
        }
    }

    private fun goToOnboardingOrLogin(invite: InviteModel?) {
        val direction = if (viewModel.hasSeenOnboarding() || invite != null) {
            HomeFragmentDirections.actionHomeFragmentToSocialLoginFragment()
        } else {
            HomeFragmentDirections.actionHomeFragmentToOnboardingFragment()
        }
        findNavController().navigate(direction)
    }

    private fun goToDashboard(user: User?) {
        val toDashboard = HomeFragmentDirections.actionHomeFragmentToDashboardFragment(user)
        findNavController().navigate(toDashboard)
    }
}