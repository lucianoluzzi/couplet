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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentDashboardBindingImpl
import com.couplesdating.couplet.domain.model.Idea
import com.couplesdating.couplet.ui.dashboard.adapter.CategoryAdapter
import com.couplesdating.couplet.ui.dashboard.adapter.CategoryUIModel
import com.couplesdating.couplet.ui.dashboard.model.Banner
import com.couplesdating.couplet.ui.dashboard.model.DashboardRoute
import com.couplesdating.couplet.ui.dashboard.model.DashboardUIState
import com.couplesdating.couplet.ui.extensions.doNothing
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.textValue
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DashboardFragment : Fragment() {
    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentDashboardBindingImpl.inflate(layoutInflater)
    }
    private val viewModel by viewModel<DashboardViewModel> {
        parametersOf(user)
    }

    private val navigationArgs: DashboardFragmentArgs by navArgs()
    private val user by lazy {
        navigationArgs.user
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    private fun navigate(navigationRoute: DashboardRoute) {
        when (navigationRoute) {
            is DashboardRoute.ToIdeas -> navigateToIdeas(navigationRoute.ideas)
            DashboardRoute.ToSync -> navigateToSync()
        }
    }

    private fun navigateToIdeas(ideas: List<Idea>) {
        val toIdeas = DashboardFragmentDirections.actionDashboardFragmentToIdeaListFragment(
            ideas.toTypedArray()
        )
        findNavController().navigate(toIdeas)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        decorateTitle()
        viewModel.uiData.observe(viewLifecycleOwner) { uiState ->
            handleUIState(uiState)
        }
        lifecycleScope.launch {
            viewModel.navigationFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { navigationRoute ->
                    navigate(navigationRoute)
                }
        }
    }

    private fun decorateTitle() {
        user?.let {
            if (!it.firstName.isNullOrBlank()) {
                binding.toCouplet.text = "to Couplet ${it.firstName}"
            }
        }

        val titleText = binding.toCouplet.textValue()
        val spannable = SpannableString(titleText)
        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        val color = requireContext().getColor(R.color.red)
        val wordToDecorate = if (!user?.firstName.isNullOrBlank()) {
            user?.firstName ?: "Couplet"
        } else {
            "Couplet"
        }
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
        binding.categories.adapter = CategoryAdapter(categories) { categoryClickedId ->
            viewModel.onCategoryClicked(categoryClickedId)
        }
    }

    private fun handleBannerState(banner: Banner?) {
        when (banner) {
            is Banner.PendingInvite -> setPendingInviteBanner(banner)
            Banner.RegisterPartner -> setRegisterPartnerBanner(banner)
            Banner.BecomePremium -> setBecomePremiumBanner(banner)
            is Banner.NewMatches -> setNewMatchesClicked(banner)
        }
    }

    private fun setNewMatchesClicked(banner: Banner.NewMatches) {
        binding.newMatchesBanner.isVisible = true
        binding.newMatchesBanner.setOnClickListener {
            viewModel.onBannerClicked(banner)
        }
    }

    private fun setBecomePremiumBanner(banner: Banner) {
        binding.becomePremiumBanner.isVisible = true
        binding.becomePremiumBanner.setOnClickListener {
            viewModel.onBannerClicked(banner)
        }
    }

    private fun setPendingInviteBanner(banner: Banner.PendingInvite) = with(binding) {
        pendingInviteBanner.isVisible = true
        val description = pendingInviteBanner.findViewById<TextView>(R.id.description)
        if (banner.invite.inviterDisplayName.isNotBlank()) {
            description.text = user?.let {
                "${it.firstName}, you have a pending invite from ${banner.invite.inviterDisplayName}"
            } ?: "You have a pending invite from ${banner.invite.inviterDisplayName}"
        }
        pendingInviteBanner.setOnClickListener {
            viewModel.onBannerClicked(banner)
        }
    }

    private fun setRegisterPartnerBanner(banner: Banner) = with(binding) {
        registerPartnerBanner.isVisible = true
        val description = registerPartnerBanner.findViewById<TextView>(R.id.description)
        description.text = user?.let {
            "${it.firstName}, we are almost there. Register a partner and get kinky!"
        } ?: "We are almost there. Register a partner and get kinky!"
        registerPartnerBanner.setOnClickListener {
            viewModel.onBannerClicked(banner)
        }
    }

    private fun navigateToSync() {
        val toInvitePartner =
            DashboardFragmentDirections.actionDashboardFragmentToInvitePartnerFragment()
        findNavController().navigate(toInvitePartner)
    }
}