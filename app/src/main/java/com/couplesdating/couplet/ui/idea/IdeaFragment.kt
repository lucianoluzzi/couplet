package com.couplesdating.couplet.ui.idea

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.couplesdating.couplet.databinding.FragmentIdeaBindingImpl
import com.couplesdating.couplet.domain.model.Idea

class IdeaFragment : Fragment() {
    private val idea by lazy<Idea?> {
        requireArguments().getParcelable(IDEA)
    }
    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentIdeaBindingImpl.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.description.text = idea?.description
    }

    companion object {
        private const val IDEA = "IDEA"

        fun newInstance(idea: Idea) = IdeaFragment().apply {
            arguments = bundleOf(IDEA to idea)
        }
    }
}