package com.couplesdating.couplet.ui.pendingInvite

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentPendingInviteBinding
import com.couplesdating.couplet.ui.extensions.getMediumFormatString
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.textValue

class PendingInviteFragment : Fragment() {
    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentPendingInviteBinding.inflate(layoutInflater)
    }
    private val navigationArgs: PendingInviteFragmentArgs by navArgs()
    private val invite by lazy {
        navigationArgs.invite
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
        setTitle()
        setInviteContent()
    }

    private fun setTitle() = with(binding) {
        title.text = user.firstName?.let {
            getString(R.string.pending_invite_title, user.firstName)
        } ?: getString(R.string.pending_invite_title_no_name)
        val titleText = title.textValue()
        val spannable = SpannableString(titleText)
        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        val color = requireContext().getColor(R.color.red)
        spannable.setColor(
            color = color,
            wordToDecorate = user.firstName ?: "pending invite",
            wholeText = titleText
        )
        spannable.setFont(
            typeface = medium,
            wordToDecorate = user.firstName ?: "pending invite",
            wholeText = titleText
        )
        title.text = spannable
    }

    private fun setInviteContent() = with(binding) {
        inviterName.text = invite.inviterDisplayName
        invite.timestamp?.let {
            date.isVisible = true
            date.text = it.getMediumFormatString(requireContext())
        }
        invite.note?.let {
            note.isVisible = true
            note.text = it
        }
    }
}