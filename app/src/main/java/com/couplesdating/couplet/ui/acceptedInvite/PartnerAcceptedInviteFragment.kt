package com.couplesdating.couplet.ui.acceptedInvite

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentPartnerAcceptedInviteBinding
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.textValue

class PartnerAcceptedInviteFragment : Fragment() {
    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentPartnerAcceptedInviteBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle()
        decorateTitle()
        binding.getKinky.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setTitle() {
        val pairInfo = requireArguments().getString("pair_info")
        binding.title.text = pairInfo
    }

    private fun decorateTitle() = with(binding) {
        val titleValue = title.textValue()
        val wordToDecorate = if (titleValue.contains("partner")) {
            "partner"
        } else {
            titleValue.split(",")[0]
        }
        val spannable = SpannableString(titleValue)
        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        val color = requireContext().getColor(R.color.red)
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
}