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
import com.couplesdating.couplet.domain.model.Match
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
            is DashboardRoute.ToIdeas -> navigateToIdeas(
                category = navigationRoute.category,
                ideas = navigationRoute.ideas
            )
            DashboardRoute.ToSync -> navigateToSync()
        }
    }

    private fun navigateToIdeas(
        category: CategoryUIModel,
        ideas: List<Idea>
    ) {
        val toIdeas = DashboardFragmentDirections.actionDashboardFragmentToIdeaListFragment(
            ideas = ideas.toTypedArray(),
            category = category
        )
        findNavController().navigate(toIdeas)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        decorateTitle()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { navigationRoute ->
                    navigate(navigationRoute)
                }
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingContainer.isVisible = isLoading
        }
        viewModel.uiDataMediator.observe(viewLifecycleOwner) { uiState ->
            handleUIState(uiState)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchBanner()
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
                setCategories(uiState.categories)
                handleBannerState(uiState.banner)
            }
            null -> doNothing
        }
    }

    private fun setCategories(categories: List<CategoryUIModel>) {
        binding.categories.adapter = CategoryAdapter(categories) { categoryClicked ->
            if (categoryClicked.ideas.isNotEmpty()) {
                viewModel.onCategoryClicked(categoryClicked)
            } else {
                val toEmptyList =
                    DashboardFragmentDirections.actionDashboardFragmentToEmptyListFragment()
                findNavController().navigate(toEmptyList)
            }
        }
    }

    private fun handleBannerState(banner: Banner?) {
        when (banner) {
            is Banner.PendingInvite -> setPendingInviteBanner(banner)
            is Banner.RegisterPartner -> setRegisterPartnerBanner(banner)
            is Banner.BecomePremium -> setBecomePremiumBanner(banner)
            is Banner.NewMatches -> setNewMatchesClicked(banner)
            null -> {
                with(binding) {
                    registerPartnerBanner.isVisible = false
                    pendingInviteBanner.isVisible = false
                    becomePremiumBanner.isVisible = false
                    binding.newMatchesBanner.isVisible = false
                }
            }
        }
    }

    private fun setNewMatchesClicked(banner: Banner.NewMatches) = with(binding) {
        registerPartnerBanner.isVisible = false
        pendingInviteBanner.isVisible = false
        becomePremiumBanner.isVisible = false
        binding.newMatchesBanner.isVisible = true
        binding.newMatchesBanner.setOnClickListener {
            viewModel.onBannerClicked(banner)
            navigateToMatches(banner.newMatches)
        }
    }

    private fun navigateToMatches(newMatches: List<Match>) {
        user?.let {
            val toMatches =
                DashboardFragmentDirections.actionDashboardFragmentToMatchesFragment(it)
            findNavController().navigate(toMatches)
        }
    }

    private fun setBecomePremiumBanner(banner: Banner) = with(binding) {
        registerPartnerBanner.isVisible = false
        pendingInviteBanner.isVisible = false
        newMatchesBanner.isVisible = false
        becomePremiumBanner.isVisible = true
        becomePremiumBanner.setOnClickListener {
            viewModel.onBannerClicked(banner)
        }
    }

    private fun setPendingInviteBanner(banner: Banner.PendingInvite) = with(binding) {
        newMatchesBanner.isVisible = false
        registerPartnerBanner.isVisible = false
        becomePremiumBanner.isVisible = false
        pendingInviteBanner.isVisible = true
        val description = pendingInviteBanner.findViewById<TextView>(R.id.description)
        if (banner.invite.inviterDisplayName.isNotBlank()) {
            description.text = user?.let {
                "${it.firstName}, you have a pending invite from ${banner.invite.inviterDisplayName}"
            } ?: "You have a pending invite from ${banner.invite.inviterDisplayName}"
        }
        pendingInviteBanner.setOnClickListener {
            viewModel.onBannerClicked(banner)
            user?.let { user ->
                val toPendingInvite =
                    DashboardFragmentDirections.actionDashboardFragmentToPendingInviteFragment(
                        invite = banner.invite,
                        user = user
                    )
                findNavController().navigate(toPendingInvite)
            }
        }
    }

    private fun setRegisterPartnerBanner(banner: Banner) = with(binding) {
        newMatchesBanner.isVisible = false
        becomePremiumBanner.isVisible = false
        pendingInviteBanner.isVisible = false
        registerPartnerBanner.isVisible = true
        val description = registerPartnerBanner.findViewById<TextView>(R.id.description)
        description.text = user?.let {
            "${it.firstName}, we are almost there. Register a partner and get kinky!"
        } ?: "We are almost there. Register a partner and get kinky!"
        registerPartnerBanner.setOnClickListener {
            viewModel.onBannerClicked(banner)
            navigateToSync()
        }
    }

    private fun navigateToSync() {
        val toInvitePartner =
            DashboardFragmentDirections.actionDashboardFragmentToInvitePartnerFragment()
        findNavController().navigate(toInvitePartner)
    }
}