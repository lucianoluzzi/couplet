package com.couplesdating.couplet.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.couplesdating.couplet.databinding.FragmentDashboardBindingImpl

class DashboardFragment(private val viewModel: DashboardViewModel) : Fragment() {
    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentDashboardBindingImpl.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.shouldShowSyncScreen.observe(viewLifecycleOwner) { shouldShowSync ->
            if (shouldShowSync) {
                viewModel.onSyncShown()
                navigateToSync()
            }
        }
        return binding.root
    }

    private fun navigateToSync() {
        val toInvitePartner =
            DashboardFragmentDirections.actionDashboardFragmentToInvitePartnerFragment()
        findNavController().navigate(toInvitePartner)
    }
}