package com.couplesdating.couplet.ui.idea

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentIdeaBindingImpl
import com.couplesdating.couplet.domain.model.Idea
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.textValue
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class IdeaFragment : Fragment() {
    private val viewModel by viewModel<IdeaViewModel>()
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
        idea?.let {
            viewModel.init(it)
        }
        binding.description.text = idea?.description

        lifecycleScope.launch {
            viewModel.navigationFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { uiModel ->
                    decorate(uiModel)
                }
        }
    }

    private fun decorate(ideaUIModel: IdeaUIModel) {
        val description = binding.description.textValue()
        val spannable = SpannableString(description)

        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        ideaUIModel.wordsToDecorate?.forEach {
            spannable.setFont(
                typeface = medium,
                wordToDecorate = it,
                wholeText = description
            )
        }
        binding.description.text = spannable
    }

    companion object {
        private const val IDEA = "IDEA"

        fun newInstance(idea: Idea) = IdeaFragment().apply {
            arguments = bundleOf(IDEA to idea)
        }
    }
}