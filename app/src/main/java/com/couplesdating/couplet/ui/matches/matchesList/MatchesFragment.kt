package com.couplesdating.couplet.ui.matches.matchesList

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.view.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentMatchesBindingImpl
import com.couplesdating.couplet.domain.model.Match
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.ui.extensions.*
import com.couplesdating.couplet.ui.matches.adapter.MatchAdapter
import com.couplesdating.couplet.ui.widgets.ItemMarginDecorator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MatchesFragment(
    private val viewModel: MatchesViewModel
) : Fragment() {
    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentMatchesBindingImpl.inflate(layoutInflater)
    }
    private val navigationArgs by navArgs<MatchesFragmentArgs>()
    private val user by lazy {
        navigationArgs.user
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.matches_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all -> {
                showDeleteAllDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDeleteAllDialog() {
        viewModel.onDeleteAllClicked()
        showAlertDialog(
            title = "Delete all matches?",
            message = "This cannot be undone.",
            negativeButtonClickAction = {
                viewModel.onDeleteCancel()
            },
            positiveButtonClickAction = {
                viewModel.onDeleteAllConfirm(user)
            },
            positiveButtonText = "Confirm"
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        decorateTitle()
        setLabel()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateFlow.collect { matchesUIState ->
                    handleUIState(matchesUIState)
                }
            }
        }
    }

    private fun handleUIState(matchesUIState: MatchesUIState) {
        when (matchesUIState) {
            MatchesUIState.DeletedMatches -> {
                binding.loadingContainer.isVisible = false
                navigateToEmptyMatches(user)
            }
            is MatchesUIState.Error -> {
                binding.loadingContainer.isVisible = false
                showError(matchesUIState.errorMessage)
            }
            MatchesUIState.Loading -> binding.loadingContainer.isVisible = true
            is MatchesUIState.Success -> {
                binding.loadingContainer.isVisible = false
                setMatches(matchesUIState.matches)
            }
        }
    }

    private fun navigateToEmptyMatches(user: User) {
        val toEmptyMatches =
            MatchesFragmentDirections.actionMatchesFragmentToEmptyMatchesFragment(
                user = user
            )
        findNavController().navigate(toEmptyMatches)
    }

    private fun decorateTitle() {
        val titleText = binding.title.textValue()
        val spannable = SpannableString(titleText)
        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        val color = requireContext().getColor(R.color.red)
        spannable.setColor(
            color = color,
            wordToDecorate = titleText,
            wholeText = titleText
        )
        spannable.setFont(
            typeface = medium,
            wordToDecorate = titleText,
            wholeText = titleText
        )

        binding.title.text = spannable
    }

    private fun setMatches(matches: List<Match>) = with(binding) {
        val matchAdapter = MatchAdapter(matches) {
            viewModel.onMatchClicked()
            navigateToMatchesDetailList(it)
        }
        categories.adapter = matchAdapter
        val itemDecorationCount = categories.itemDecorationCount
        if (itemDecorationCount == 0) {
            categories.addItemDecoration(
                ItemMarginDecorator(6)
            )
        }
    }

    private fun setLabel() {
        user.pairedPartner?.let {
            binding.label.text = getString(R.string.matches_label, user.firstName, it.firstName)
        }
    }

    private fun navigateToMatchesDetailList(matchPosition: Int) {
        val toMatchDetailsList =
            MatchesFragmentDirections.actionMatchesFragmentToMatchDetailFragment(
                user = user,
                matchPosition = matchPosition
            )
        findNavController().navigate(toMatchDetailsList)
    }
}