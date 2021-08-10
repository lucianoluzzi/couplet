package com.couplesdating.couplet.ui.matches

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentMatchesDetailListBinding
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.textValue
import com.couplesdating.couplet.ui.matches.adapter.MatchDetailAdapter
import com.couplesdating.couplet.ui.widgets.ViewPager2ViewHeightAnimator

class MatchesDetailListFragment : Fragment() {
    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentMatchesDetailListBinding.inflate(layoutInflater)
    }
    private val navigationArgs by navArgs<MatchesDetailListFragmentArgs>()
    private val matches by lazy {
        navigationArgs.matches
    }
    private val user by lazy {
        navigationArgs.user
    }
    private val initialIdeaPosition by lazy {
        navigationArgs.matchPosition
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMatchesPager()
    }

    private fun decorateTitle(matchPosition: Int) {
        val matchNumber = matchPosition + 1
        binding.title.text = "Match $matchNumber"
        val titleText = binding.title.textValue()
        val spannable = SpannableString(titleText)
        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        val color = requireContext().getColor(R.color.red)
        spannable.setColor(
            color = color,
            wordToDecorate = matchNumber.toString(),
            wholeText = titleText
        )
        spannable.setFont(
            typeface = medium,
            wordToDecorate = matchNumber.toString(),
            wholeText = titleText
        )

        binding.title.text = spannable
    }

    private fun setMatchesPager() = with(binding) {
        user.pairedPartner?.firstName?.let {
            val matchDetailAdapter = MatchDetailAdapter(
                partnerName = it,
                matches = matches.toList(),
                matchesDetailListFragment = this@MatchesDetailListFragment
            )
            matchesPager.adapter = matchDetailAdapter
            matchesPager.offscreenPageLimit = 3
            val viewPager2ViewHeightAnimator = ViewPager2ViewHeightAnimator()
            viewPager2ViewHeightAnimator.viewPager2 = matchesPager
            matchesPager.post {
                matchesPager.currentItem = initialIdeaPosition
            }

            matchesPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    decorateTitle(position)
                }
            })
        }
    }
}