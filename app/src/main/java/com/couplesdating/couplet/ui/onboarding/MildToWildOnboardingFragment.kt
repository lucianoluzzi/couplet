package com.couplesdating.couplet.ui.onboarding

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentMildToWildBinding
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.textValue


class MildToWildOnboardingFragment : Fragment() {
    private lateinit var binding: FragmentMildToWildBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMildToWildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        decorateLabel()
    }

    private fun decorateLabel() {
        val title = binding.wild.textValue()
        val spannable = SpannableString(title)

        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        val color = requireContext().getColor(R.color.red)
        spannable.setColor(color, "wild", title)
        spannable.setFont(medium, "wild", title)
        binding.wild.text = spannable
    }
}