package com.couplesdating.couplet.ui.idea

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.couplesdating.couplet.domain.model.Idea

class IdeaPagerAdapter(
    private val ideaList: List<Idea>,
    private val ideaListFragment: IdeaListFragment
) : FragmentStateAdapter(ideaListFragment) {

    override fun getItemCount() = ideaList.size

    override fun createFragment(position: Int): Fragment {
        TODO("Not yet implemented")
    }
}