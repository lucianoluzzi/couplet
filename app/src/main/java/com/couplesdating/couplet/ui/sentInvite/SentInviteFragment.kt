package com.couplesdating.couplet.ui.sentInvite

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
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentSentInviteBinding
import com.couplesdating.couplet.ui.extensions.*
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class SentInviteFragment(
    private val viewModel: SentInviteViewModel
) : Fragment() {
    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentSentInviteBinding.inflate(layoutInflater)
    }
    private val navArgs by navArgs<SentInviteFragmentArgs>()
    private val invite by lazy {
        navArgs.invite
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.deleteInviteLiveData.observe(viewLifecycleOwner) {
            handleUIState(it)
        }
        decorateTitle()
        setInviteContent()
        setButtons()
    }

    private fun handleUIState(uiState: SentInviteUIState) {
        when (uiState) {
            SentInviteUIState.Error -> {
                binding.loadingContainer.isVisible = false
                showError()
            }
            SentInviteUIState.Loading -> {
                binding.loadingContainer.isVisible = true
            }
            SentInviteUIState.Success -> {
                binding.loadingContainer.isVisible = false
                Snackbar
                    .make(binding.root, "Invite deleted", BaseTransientBottomBar.LENGTH_LONG)
                    .show()
                findNavController().popBackStack()
            }
        }
    }

    private fun decorateTitle() = with(binding) {
        val titleText = title.textValue()
        val spannable = SpannableString(titleText)
        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        val color = requireContext().getColor(R.color.red)
        spannable.setColor(
            color = color,
            wordToDecorate = "invite",
            wholeText = titleText
        )
        spannable.setFont(
            typeface = medium,
            wordToDecorate = "invite",
            wholeText = titleText
        )
        title.text = spannable
    }

    private fun setInviteContent() = with(binding) {
        invite.timestamp?.let {
            date.isVisible = true
            date.text = it.getMediumFormatString(requireContext())
        }
    }

    private fun setButtons() = with(binding) {
        deleteInvite.setOnClickListener {
            viewModel.onDeleteClick()
            showAlertDialog(
                title = "Delete invite",
                message = "You cannot undo this action",
                positiveButtonText = "Delete",
                positiveButtonClickAction = {
                    viewModel.onDeleteConfirm(invite)
                },
                negativeButtonClickAction = {
                    viewModel.onDeleteCancel()
                }
            )
        }
    }
}