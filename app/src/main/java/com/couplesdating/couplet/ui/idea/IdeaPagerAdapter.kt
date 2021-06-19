package com.couplesdating.couplet.ui.idea

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.couplesdating.couplet.domain.model.Idea

class IdeaPagerAdapter(
    private val categoryName: String,
    private val ideaList: List<Idea>,
    ideaListFragment: IdeaListFragment
) : FragmentStateAdapter(ideaListFragment) {

    override fun getItemCount() = ideaList.size

    override fun createFragment(position: Int) = getIdeaFragment(ideaList[position])

    private fun getIdeaFragment(idea: Idea) = IdeaFragment.newInstance(
        categoryName = categoryName,
        idea = idea
    )
}