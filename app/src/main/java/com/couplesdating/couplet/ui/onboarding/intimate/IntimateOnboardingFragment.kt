package com.couplesdating.couplet.ui.onboarding.intimate

import android.content.Context
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
import com.couplesdating.couplet.databinding.FragmentIntimateOnboardingBinding
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.textValue
import org.koin.android.viewmodel.ext.android.viewModel

class IntimateOnboardingFragment : Fragment() {
    private lateinit var binding: FragmentIntimateOnboardingBinding
    private val viewModel by viewModel<IntimateOnboardingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntimateOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        decorateTitle()
        binding.continueButton.setOnClickListener {
            viewModel.onHellYeahClicked()
            setOnboardingShown()
            val toSocialLogin =
                IntimateOnboardingFragmentDirections.actionIntimateOnboardingFragmentToSocialLoginFragment()
            findNavController().navigate(toSocialLogin)
        }
    }

    private fun decorateTitle() {
        val title = binding.happier.textValue()
        val spannable = SpannableString(title)

        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        val color = requireContext().getColor(R.color.red)
        spannable.setColor(color, "happier", title)
        spannable.setFont(medium, "happier", title)
        binding.happier.text = spannable
    }

    private fun setOnboardingShown() {
        val preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        with(preferences.edit()) {
            putBoolean(getString(R.string.has_shown_onboarding_key), true)
            apply()
        }
    }
}