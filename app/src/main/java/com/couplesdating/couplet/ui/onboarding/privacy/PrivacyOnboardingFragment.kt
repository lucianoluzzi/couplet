package com.couplesdating.couplet.ui.onboarding.privacy

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
import com.couplesdating.couplet.databinding.FragmentOnboardingPrivacyBinding
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.textValue
import org.koin.android.viewmodel.ext.android.viewModel

class PrivacyOnboardingFragment : Fragment() {
    private lateinit var binding: FragmentOnboardingPrivacyBinding
    private val viewModel by viewModel<PrivacyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingPrivacyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        decorateTitle()
        binding.continueButton.setOnClickListener {
            viewModel.onContinueClicked()
            val toIntimateFragment =
                PrivacyOnboardingFragmentDirections.actionPrivacyOnboardingFragmentToIntimateOnboardingFragment()
            findNavController().navigate(toIntimateFragment)
        }
    }

    private fun decorateTitle() {
        val title = binding.title.textValue()
        val spannable = SpannableString(title)

        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        val color = requireContext().getColor(R.color.red)
        spannable.setColor(color, "privacy", title)
        spannable.setFont(medium, "privacy", title)
        binding.title.text = spannable
    }
}