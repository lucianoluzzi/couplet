package com.couplesdating.couplet.ui.matches.adapter

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.couplesdating.couplet.domain.model.Match
import com.couplesdating.couplet.ui.matches.MatchDetailFragment
import com.couplesdating.couplet.ui.matches.MatchesDetailListFragment

class MatchDetailAdapter(
    private val partnerName: String,
    private val matches: List<Match>,
    matchesDetailListFragment: MatchesDetailListFragment
) : FragmentStateAdapter(matchesDetailListFragment) {

    override fun getItemCount() = matches.size

    override fun createFragment(position: Int) = MatchDetailFragment.newInstance(
        partnerName = partnerName,
        match = matches[position]
    )
}