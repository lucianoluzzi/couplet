package com.couplesdating.couplet.ui.ideaList

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.couplesdating.couplet.domain.model.Idea
import com.couplesdating.couplet.ui.idea.IdeaFragment

class IdeaPagerAdapter(
    private val ideaList: List<Idea>,
    ideaListFragment: IdeaListFragment
) : FragmentStateAdapter(ideaListFragment) {

    override fun getItemCount() = ideaList.size

    override fun createFragment(position: Int) = getIdeaFragment(ideaList[position])

    private fun getIdeaFragment(idea: Idea) = IdeaFragment.newInstance(
        idea = idea
    )
}