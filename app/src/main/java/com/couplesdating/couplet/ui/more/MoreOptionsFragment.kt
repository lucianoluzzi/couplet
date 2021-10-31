package com.couplesdating.couplet.ui.more

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentMoreBinding
import com.couplesdating.couplet.ui.extensions.*
import com.couplesdating.couplet.ui.more.model.MoreOptionsEffects
import com.couplesdating.couplet.ui.more.model.MoreOptionsIntents
import com.couplesdating.couplet.ui.more.model.MoreOptionsState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MoreOptionsFragment(
    private val viewModel: MoreOptionsViewModel
) : Fragment() {
    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentMoreBinding.inflate(layoutInflater)
    }
    private val adapter = RecentMatchesListAdapter { matchId ->
        viewModel.onIntent(MoreOptionsIntents.MatchClick(matchId))
    }

    private val navigationArgs by navArgs<MoreOptionsFragmentArgs>()
    private val matches by lazy {
        navigationArgs.matches.asList()
    }
    private val user by lazy {
        navigationArgs.user
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setIdeaList()
        decorateTitle()
        setSeeAllLabel()
        setClickListeners()
        viewModel.ideasLiveData.observe(viewLifecycleOwner) { state ->
            handleState(state)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.effectsLiveData
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { effect ->
                    handleEffect(effect)
                }
        }
        viewModel.getRecentMatches(matches)
    }

    private fun handleState(state: MoreOptionsState) {
        when (state) {
            is MoreOptionsState.WithMatchesState -> showMatchesList(state)
            MoreOptionsState.WithoutMatchesState -> showEmptyMessage()
        }
    }

    private fun handleEffect(effect: MoreOptionsEffects) {
        when (effect) {
            is MoreOptionsEffects.Share -> {
                val message = getString(R.string.more_options_share, effect.shareLink)
                startActivity(
                    requireContext().createShareIntent(message)
                )
            }
            is MoreOptionsEffects.NavigateToMatch -> {
                val clickedMatch = matches.find {
                    it.id == effect.matchId
                }
                val ideaPosition = matches.indexOf(clickedMatch)
                if (ideaPosition != -1) {
                    val toMatchDetails =
                        MoreOptionsFragmentDirections.actionMoreFragmentToMatchDetailFragment(
                            user = user,
                            matchPosition = ideaPosition
                        )
                    findNavController().navigate(toMatchDetails)
                }
            }
            is MoreOptionsEffects.NavigateToPartner -> TODO()
            is MoreOptionsEffects.NavigateToProfile -> {
                val toProfile = MoreOptionsFragmentDirections.actionMoreFragmentToProfileFragment(
                    user
                )
                findNavController().navigate(toProfile)
            }
            MoreOptionsEffects.NavigateToSeeAllMatches -> {
                val toSeeAllMatches =
                    MoreOptionsFragmentDirections.actionMoreFragmentToMatchesFragment(
                        user = user
                    )
                findNavController().navigate(toSeeAllMatches)
            }
        }
    }

    private fun showMatchesList(state: MoreOptionsState.WithMatchesState) = with(binding) {
        emptyListMessage.isVisible = false
        recentMatchesList.isVisible = true
        adapter.submitList(state.matches)
    }

    private fun showEmptyMessage() = with(binding) {
        emptyListMessage.text = getString(R.string.recent_matches_empty_message, user.firstName)
        emptyListMessage.isVisible = true
        recentMatchesList.isVisible = false
    }

    private fun setIdeaList() = with(binding) {
        if (recentMatchesList.itemDecorationCount == 0) {
            recentMatchesList.addItemDecoration(RecentMatchesItemDecorator())
        }
        recentMatchesList.layoutManager = LinearLayoutManager(requireContext())
        recentMatchesList.adapter = adapter
    }

    private fun decorateTitle() = with(binding) {
        val titleValue = title.textValue()
        val spannable = SpannableString(titleValue)
        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        val color = requireContext().getColor(R.color.red)
        val wordToDecorate = "options"
        spannable.setColor(
            color = color,
            wordToDecorate = wordToDecorate,
            wholeText = titleValue
        )
        spannable.setFont(
            typeface = medium,
            wordToDecorate = wordToDecorate,
            wholeText = titleValue
        )

        title.text = spannable
    }

    private fun setClickListeners() = with(binding) {
        seeAllLabel.setOnClickListener {
            viewModel.onIntent(MoreOptionsIntents.SeeAllMatchesClick)
        }
        viewPartnerItem.viewPartnerContainer.setOnClickListener {
            viewModel.onIntent(MoreOptionsIntents.PartnerClick)
        }
        viewProfileItem.viewProfileContainer.setOnClickListener {
            viewModel.onIntent(MoreOptionsIntents.ProfileClick)
        }
        viewShareItem.viewShareContainer.setOnClickListener {
            viewModel.onIntent(MoreOptionsIntents.ShareClick)
        }
    }

    private fun setSeeAllLabel() {
        val forgotPasswordText = binding.seeAllLabel.textValue()
        val spannable = SpannableString(forgotPasswordText)
        spannable.setUnderline(
            wordToDecorate = forgotPasswordText,
            wholeText = forgotPasswordText
        )
        binding.seeAllLabel.text = spannable
    }
}