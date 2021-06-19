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
    private val categoryName by lazy {
        requireArguments().getString(CATEGORY_NAME)
    }
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
        setTitle()
    }

    private fun setTitle() {
        binding.title.text = categoryName
    }

    companion object {
        private const val CATEGORY_NAME = "CATEGORY_NAME"
        private const val IDEA = "IDEA"

        fun newInstance(
            categoryName: String,
            idea: Idea
        ) = IdeaFragment().apply {
            arguments = bundleOf(
                CATEGORY_NAME to categoryName,
                IDEA to idea

            )
        }
    }
}