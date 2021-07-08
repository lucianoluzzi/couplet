package com.couplesdating.couplet.ui.emptyList

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
import com.couplesdating.couplet.databinding.FragmentEmptyListBinding
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.textValue

class EmptyListFragment : Fragment() {

    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentEmptyListBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        decorateTitle()
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
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
        spannable.setColor(
            color = color,
            wordToDecorate = "New ideas",
            wholeText = title
        )
        spannable.setFont(
            typeface = medium,
            wordToDecorate = "New ideas",
            wholeText = title
        )

        binding.title.text = spannable
    }
}