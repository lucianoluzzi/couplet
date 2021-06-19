package com.couplesdating.couplet.ui.idea

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentIdeaListBindingImpl
import com.couplesdating.couplet.ui.utils.CircularOutlineProvider

class IdeaListFragment : Fragment() {
    private val navigationArgs by navArgs<IdeaListFragmentArgs>()
    private val ideas by lazy {
        navigationArgs.ideas.toList()
    }
    private val category by lazy {
        navigationArgs.category
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
        setButtonsElevation()
        with(binding) {
            illustration.setImageDrawable(
                ContextCompat.getDrawable(requireContext(), category.imageBig)
            )
            title.text = category.title
            pager.adapter = IdeaPagerAdapter(
                ideaList = ideas,
                ideaListFragment = this@IdeaListFragment
            )
            pager.isUserInputEnabled = false
        }
    }

    private fun setButtonsElevation() = with(binding) {
        val outlineProvider = CircularOutlineProvider()
        no.outlineProvider = outlineProvider
        yes.outlineProvider = outlineProvider
        maybe.outlineProvider = outlineProvider

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            no.outlineAmbientShadowColor =
                ContextCompat.getColor(requireContext(), R.color.no_color)
            no.outlineSpotShadowColor =
                ContextCompat.getColor(requireContext(), R.color.no_color)
            yes.outlineAmbientShadowColor = ContextCompat.getColor(requireContext(), R.color.red)
            yes.outlineSpotShadowColor = ContextCompat.getColor(requireContext(), R.color.red)
            maybe.outlineAmbientShadowColor =
                ContextCompat.getColor(requireContext(), R.color.maybe_color)
            maybe.outlineSpotShadowColor =
                ContextCompat.getColor(requireContext(), R.color.maybe_color)
        }
    }
}