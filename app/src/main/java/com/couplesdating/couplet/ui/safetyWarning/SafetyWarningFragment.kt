package com.couplesdating.couplet.ui.safetyWarning

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
import com.couplesdating.couplet.databinding.FragmentSafetyWarningBinding
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.textValue

class SafetyWarningFragment : Fragment() {

    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentSafetyWarningBinding.inflate(layoutInflater)
    }
    private val navigationArgs by navArgs<SafetyWarningFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        decorateTitle()
        setButtons()
    }

    private fun decorateTitle() = with(binding) {
        val titleValue = title.textValue()
        val spannable = SpannableString(titleValue)
        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        val color = requireContext().getColor(R.color.red)
        val wordsToDecorate = listOf(
            "Fun",
            "safety"
        )
        wordsToDecorate.forEach { word ->
            spannable.setColor(
                color = color,
                wordToDecorate = word,
                wholeText = titleValue
            )
            spannable.setFont(
                typeface = medium,
                wordToDecorate = word,
                wholeText = titleValue
            )
        }

        title.text = spannable
    }

    private fun setButtons() = with(binding) {
        goBackButton.setOnClickListener {
            // should track first
            findNavController().popBackStack()
        }
        continueButton.setOnClickListener {
            // should track first
            val toIdeaList =
                SafetyWarningFragmentDirections.actionSafetyWarningFragmentToIdeaListFragment(
                    ideas = navigationArgs.ideas,
                    category = navigationArgs.category
                )
            findNavController().navigate(toIdeaList)
        }
    }
}