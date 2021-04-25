package com.couplesdating.couplet.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.couplesdating.couplet.databinding.FragmentDashboardBindingImpl

class DashboardFragment(
    private val viewModel: DashboardViewModel
) : Fragment() {
    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentDashboardBindingImpl.inflate(layoutInflater)
    }
    private val navigationArgs: DashboardFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            handleUIState(uiState)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUser = navigationArgs.user
        viewModel.init(currentUser)
    }

    private fun handleUIState(uiState: DashboardUIState?) {
        when (uiState) {
            DashboardUIState.ShowSync -> {
                viewModel.onSyncShown()
                navigateToSync()
            }
        }
    }

    private fun navigateToSync() {
        val toInvitePartner =
            DashboardFragmentDirections.actionDashboardFragmentToInvitePartnerFragment()
        findNavController().navigate(toInvitePartner)
    }
}