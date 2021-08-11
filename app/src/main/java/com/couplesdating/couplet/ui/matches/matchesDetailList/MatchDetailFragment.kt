package com.couplesdating.couplet.ui.matches.matchesDetailList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentMatchDetailItemBinding
import com.couplesdating.couplet.domain.model.Match

class MatchDetailFragment : Fragment() {
    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentMatchDetailItemBinding.inflate(layoutInflater)
    }
    private val match by lazy<Match?> {
        requireArguments().getParcelable(MATCH)
    }
    private val partnerName by lazy {
        requireArguments().getString(PARTNER_NAME)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            description.text = match?.idea?.description
            label.text = getString(R.string.partner_response, partnerName ?: "Your partner")
        }
        setResponse()
    }

    private fun setResponse() = with(binding) {
        match?.partnerResponse?.let {
            when (it) {
                "YES" -> {
                    yesContainer.isVisible = true
                    noContainer.isVisible = false
                    maybeContainer.isVisible = false
                }
                "NO" -> {
                    yesContainer.isVisible = false
                    noContainer.isVisible = true
                    maybeContainer.isVisible = false
                }
                "MAYBE" -> {
                    yesContainer.isVisible = false
                    noContainer.isVisible = false
                    maybeContainer.isVisible = true
                }
            }
        }
    }

    companion object {
        private const val MATCH = "MATCH"
        private const val PARTNER_NAME = "PARTNER_NAME"

        fun newInstance(
            partnerName: String,
            match: Match
        ) = MatchDetailFragment().apply {
            arguments = bundleOf(
                MATCH to match,
                PARTNER_NAME to partnerName
            )
        }
    }
}