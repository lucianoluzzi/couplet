package com.couplesdating.couplet.ui.idea

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.couplesdating.couplet.databinding.FragmentIdeaListBindingImpl

class IdeaListFragment : Fragment() {
    private val navigationArgs by navArgs<IdeaListFragmentArgs>()
    private val ideas by lazy {
        navigationArgs.ideas.toList()
    }
    private val categoryName by lazy {
        navigationArgs.categoryName
    }
    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentIdeaListBindingImpl.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.pager) {
            adapter = IdeaPagerAdapter(
                ideaList = ideas,
                categoryName = categoryName,
                ideaListFragment = this@IdeaListFragment
            )
            isUserInputEnabled = false
        }
    }
}