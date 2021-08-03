package com.couplesdating.couplet.ui.rejectedInvite

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentRejectedInviteBinding
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.textValue

class RejectedInviteFragment : Fragment() {
    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentRejectedInviteBinding.inflate(layoutInflater)
    }
    private val navigationArgs by navArgs<RejectedInviteFragmentArgs>()
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
        decorateTitle()
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun decorateTitle() = with(binding) {
        user.firstName?.let {
            val inviter = invite.inviterDisplayName
            val currentUser = it
            title.text = getString(R.string.rejected_invite_title, currentUser, inviter)
            val titleValue = title.textValue()
            val spannable = SpannableString(titleValue)
            val medium = Typeface.create(
                ResourcesCompat.getFont(requireContext(), R.font.medium),
                Typeface.NORMAL
            )
            val color = requireContext().getColor(R.color.red)
            spannable.setColor(
                color = color,
                wordToDecorate = currentUser,
                wholeText = titleValue
            )
            spannable.setFont(
                typeface = medium,
                wordToDecorate = currentUser,
                wholeText = titleValue
            )
            title.text = spannable
        }
    }
}