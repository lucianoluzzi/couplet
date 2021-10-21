package com.couplesdating.couplet.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.couplesdating.couplet.databinding.FragmentMoreBinding

class MoreFragment(
    private val viewModel: MoreOptionsViewModel
) : Fragment() {
    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentMoreBinding.inflate(layoutInflater)
    }
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
        binding.recentMatchesList.addItemDecoration(RecentMatchesItemDecorator())
        val adapter = RecentMatchesListAdapter()
        val recentMatches = viewModel.getRecentMatches(matches)
        binding.recentMatchesList.layoutManager = LinearLayoutManager(requireContext())
        binding.recentMatchesList.adapter = adapter
        adapter.submitList(recentMatches)
    }
}