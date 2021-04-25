package com.couplesdating.couplet.ui.invited

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
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
import com.couplesdating.couplet.databinding.FragmentInvitedBinding
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.textValue

class InvitedFragment(
    private val viewModel: InvitedViewModel
) : Fragment() {
    private lateinit var binding: FragmentInvitedBinding
    private val navigationArguments by navArgs<InvitedFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInvitedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiState.observe(viewLifecycleOwner) {
            onUiState(it)
        }
        viewModel.getData(navigationArguments.inviteId)
        setUpTexts()
        setUpButtonsClick()
    }

    private fun onUiState(uiState: InvitedUIState) {
        when (uiState) {
            is InvitedUIState.AcceptedInvite -> findNavController().popBackStack()
            is InvitedUIState.RejectedInvite -> findNavController().popBackStack()
            is InvitedUIState.InviteExists -> {
                if (!uiState.exists) {
                    findNavController().popBackStack()
                } else {
                    binding.loadingContainer.isVisible = false
                    binding.contentContainer.isVisible = true
                }
            }
            InvitedUIState.SameUser -> findNavController().popBackStack()
        }
    }

    private fun setUpTexts() = with(binding) {
        decorateTitle()
        setInvitedByText()

        navigationArguments.note?.let {
            note.isVisible = true
            note.text = it
        }
    }

    private fun FragmentInvitedBinding.setInvitedByText() {
        val invitedByText = invitedBy.textValue()
        val displayName = navigationArguments.displayName
        if (!displayName.isNullOrBlank()) {
            invitedBy.text = invitedByText.replace("Someone", displayName)
            invitedBy.text = decorateText(
                textToDecorate = displayName,
                fullText = invitedBy.textValue()
            )
        } else {
            invitedBy.text = decorateText(
                textToDecorate = "pair",
                fullText = invitedByText
            )
        }
    }

    private fun FragmentInvitedBinding.decorateTitle() {
        val titleText = title.textValue()
        title.text = decorateText(
            textToDecorate = "invited",
            fullText = titleText
        )
    }

    private fun decorateText(
        textToDecorate: String,
        fullText: String
    ): Spannable {
        val spannable = SpannableString(fullText)

        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        val color = requireContext().getColor(R.color.red)
        spannable.setColor(color, textToDecorate, fullText)
        spannable.setFont(medium, textToDecorate, fullText)

        return spannable
    }

    private fun setUpButtonsClick() {
        binding.accept.setOnClickListener {
            viewModel.onInviteAccepted(
                inviterId = navigationArguments.id,
                inviteId = navigationArguments.inviteId
            )
        }
        binding.close.setOnClickListener {
            viewModel.onInviteRejected()
        }
    }
}