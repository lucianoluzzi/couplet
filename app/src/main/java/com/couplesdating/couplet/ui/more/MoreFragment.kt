package com.couplesdating.couplet.ui.more

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentMoreBinding
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.setUnderline
import com.couplesdating.couplet.ui.extensions.textValue

class MoreFragment(
    private val viewModel: MoreOptionsViewModel
) : Fragment() {
    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentMoreBinding.inflate(layoutInflater)
    }
    private val adapter = RecentMatchesListAdapter()

    private val navigationArgs by navArgs<MoreFragmentArgs>()
    private val matches by lazy {
        navigationArgs.matches.asList()
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
        setIdeaList()
        decorateTitle()
        decorateSeeAllLabel()
        viewModel.ideasLiveData.observe(viewLifecycleOwner) { recentMatches ->
            adapter.submitList(recentMatches)
        }
        viewModel.getRecentMatches(matches)
    }

    private fun setIdeaList() {
        binding.recentMatchesList.addItemDecoration(RecentMatchesItemDecorator())
        binding.recentMatchesList.layoutManager = LinearLayoutManager(requireContext())
        binding.recentMatchesList.adapter = adapter
    }

    private fun decorateTitle() = with(binding) {
        val titleValue = title.textValue()
        val spannable = SpannableString(titleValue)
        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        val color = requireContext().getColor(R.color.red)
        val wordToDecorate = "options"
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

    private fun decorateSeeAllLabel() {
        val forgotPasswordText = binding.seeAllLabel.textValue()
        val spannable = SpannableString(forgotPasswordText)
        spannable.setUnderline(
            wordToDecorate = forgotPasswordText,
            wholeText = forgotPasswordText
        )
        binding.seeAllLabel.text = spannable
    }
}