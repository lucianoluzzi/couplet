package com.couplesdating.couplet.ui.dashboard

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentDashboardBindingImpl
import com.couplesdating.couplet.ui.dashboard.adapter.CategoryAdapter
import com.couplesdating.couplet.ui.dashboard.adapter.CategoryUIModel
import com.couplesdating.couplet.ui.dashboard.model.Banner
import com.couplesdating.couplet.ui.dashboard.model.DashboardUIState
import com.couplesdating.couplet.ui.extensions.doNothing
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.textValue

class DashboardFragment(
    private val viewModel: DashboardViewModel
) : Fragment() {
    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentDashboardBindingImpl.inflate(layoutInflater)
    }
    private val navigationArgs: DashboardFragmentArgs by navArgs()
    private val currentUser by lazy {
        navigationArgs.user
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.shouldShowSync.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.onSyncShown()
                navigateToSync()
            }
        }
        viewModel.uiData.observe(viewLifecycleOwner) { uiState ->
            handleUIState(uiState)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        decorateTitle()
        viewModel.init(currentUser)
    }

    private fun decorateTitle() {
        currentUser?.let { user ->
            if (!user.firstName.isNullOrBlank()) {
                binding.toCouplet.append(" ${user.firstName}")
            }
        }
        val titleText = binding.toCouplet.textValue()
        val spannable = SpannableString(titleText)
        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        val color = requireContext().getColor(R.color.red)
        val wordToDecorate = currentUser?.let { user ->
            if (!user.firstName.isNullOrBlank()) {
                user.firstName
            } else {
                "Couplet"
            }
        } ?: run { "Couplet" }
        spannable.setColor(
            color = color,
            wordToDecorate = wordToDecorate,
            wholeText = titleText
        )
        spannable.setFont(
            typeface = medium,
            wordToDecorate = wordToDecorate,
            wholeText = titleText
        )

        binding.toCouplet.text = spannable
    }

    private fun handleUIState(uiState: DashboardUIState?) {
        when (uiState) {
            is DashboardUIState.Success -> {
                binding.loadingContainer.isVisible = false
                setCategories(uiState.categories)
                handleBannerState(uiState.banner)
            }
            DashboardUIState.Loading -> {
                binding.loadingContainer.isVisible = true
            }
            null -> doNothing
        }
    }

    private fun setCategories(categories: List<CategoryUIModel>) {
        binding.categories.adapter = CategoryAdapter(categories)
    }

    private fun handleBannerState(banner: Banner?) {
        when (banner) {
            is Banner.PendingInvite -> {
                binding.pendingInviteBanner.isVisible = true
                val description =
                    binding.pendingInviteBanner.findViewById<TextView>(R.id.description)
                if (currentUser?.firstName != null && banner.invite.inviterDisplayName.isNotBlank()) {
                    description.text = "${currentUser?.firstName}, you a pending invite from ${banner.invite.inviterDisplayName}"
                }
            }
        }
    }

    private fun navigateToSync() {
        val toInvitePartner =
            DashboardFragmentDirections.actionDashboardFragmentToInvitePartnerFragment()
        findNavController().navigate(toInvitePartner)
    }
}