package com.couplesdating.couplet.ui.onboarding.mildToWild

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
import com.couplesdating.couplet.databinding.FragmentMildToWildBinding
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.textValue
import org.koin.android.viewmodel.ext.android.viewModel


class MildToWildOnboardingFragment : Fragment() {
    private lateinit var binding: FragmentMildToWildBinding
    private val viewModel by viewModel<MildToWildViewModel>()

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
        decorateTitle()
        binding.continueButton.setOnClickListener {
            viewModel.onContinueClicked()
            val toPrivacyFragment =
                MildToWildOnboardingFragmentDirections.actionMildToWildOnboardingFragmentToPrivacyOnboardingFragment()
            findNavController().navigate(toPrivacyFragment)
        }
    }

    private fun decorateTitle() {
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