package com.couplesdating.couplet.analytics.events.idea

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.couplesdating.couplet.databinding.FragmentIdeaListBindingImpl
import com.couplesdating.couplet.domain.model.Idea

class IdeaListFragment(
    private val ideas: List<Idea>
) : Fragment() {

    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentIdeaListBindingImpl.inflate(layoutInflater)
    }
    private val pagerAdapter by lazy {
        IdeaPagerAdapter(
            ideaList = ideas,
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pager.adapter = pagerAdapter
    }
}