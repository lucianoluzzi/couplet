package com.couplesdating.couplet.ui.idea

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.couplesdating.couplet.domain.model.Idea

class IdeaPagerAdapter(
    private val ideaList: List<Idea>,
    ideaListFragment: IdeaListFragment
) : FragmentStateAdapter(ideaListFragment) {

    override fun getItemCount() = ideaList.size

    override fun createFragment(position: Int) = IdeaFragment(
        idea = ideaList[position]
    )
}