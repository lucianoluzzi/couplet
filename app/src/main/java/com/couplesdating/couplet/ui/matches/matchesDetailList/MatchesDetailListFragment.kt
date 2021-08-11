package com.couplesdating.couplet.ui.matches.matchesDetailList

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentMatchesDetailListBinding
import com.couplesdating.couplet.domain.model.Match
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.showError
import com.couplesdating.couplet.ui.extensions.textValue
import com.couplesdating.couplet.ui.matches.adapter.MatchDetailAdapter
import com.couplesdating.couplet.ui.widgets.ViewPager2ViewHeightAnimator

class MatchesDetailListFragment(
    private val viewModel: MatchesDetailListViewModel
) : Fragment() {
    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentMatchesDetailListBinding.inflate(layoutInflater)
    }
    private val navigationArgs by navArgs<MatchesDetailListFragmentArgs>()

    private val user by lazy {
        navigationArgs.user
    }
    private val initialIdeaPosition by lazy {
        navigationArgs.matchPosition
    }
    private val matches = mutableListOf<Match>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (matches.isEmpty()) {
            matches.addAll(navigationArgs.matches)
        }
        setMatchesPager(initialIdeaPosition)
        setDeleteButton()
        viewModel.uiState.observe(viewLifecycleOwner) {
            handleUIState(it)
        }
    }

    private fun setMatchesPager(positionToShow: Int) = with(binding) {
        user.pairedPartner?.firstName?.let {
            val matchDetailAdapter = MatchDetailAdapter(
                partnerName = it,
                matches = matches.toList(),
                matchesDetailListFragment = this@MatchesDetailListFragment
            )
            matchesPager.adapter = matchDetailAdapter
            matchesPager.offscreenPageLimit = 3
            val viewPager2ViewHeightAnimator = ViewPager2ViewHeightAnimator()
            viewPager2ViewHeightAnimator.viewPager2 = matchesPager

            matchesPager.post {
                matchesPager.currentItem = positionToShow
            }

            matchesPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    decorateTitle(position)
                }
            })
        }
    }

    private fun decorateTitle(matchPosition: Int) {
        val matchNumber = matchPosition + 1
        binding.title.text = "Match $matchNumber"
        val titleText = binding.title.textValue()
        val spannable = SpannableString(titleText)
        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        val color = requireContext().getColor(R.color.red)
        spannable.setColor(
            color = color,
            wordToDecorate = matchNumber.toString(),
            wholeText = titleText
        )
        spannable.setFont(
            typeface = medium,
            wordToDecorate = matchNumber.toString(),
            wholeText = titleText
        )

        binding.title.text = spannable
    }

    private fun setDeleteButton() {
        binding.delete.setOnClickListener {
            val currentItemIndex = binding.matchesPager.currentItem
            viewModel.onDeleteClick(matches[currentItemIndex])
        }
    }

    private fun handleUIState(uiState: MatchesDetailListUIState) = with(binding) {
        when (uiState) {
            MatchesDetailListUIState.Loading -> loadingContainer.isVisible = true
            is MatchesDetailListUIState.Error -> showError(uiState.errorMessage)
            is MatchesDetailListUIState.Success -> {
                loadingContainer.isVisible = false
                content.isVisible = true
                handleRemainingList(uiState.deletedMatch)
            }
        }
    }

    private fun handleRemainingList(match: Match) {
        matches.remove(match)
        if (matches.isEmpty()) {
            navigateToEmptyList()
        } else {
            setMatchesPager(0)
        }
    }

    private fun navigateToEmptyList() {
        val toEmptyMatchesFragment =
            MatchesDetailListFragmentDirections.actionMatchDetailFragmentToEmptyMatchesFragment(
                user
            )
        findNavController().navigate(toEmptyMatchesFragment)
    }
}