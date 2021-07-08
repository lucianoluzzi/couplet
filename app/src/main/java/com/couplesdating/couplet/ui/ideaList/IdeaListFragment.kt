package com.couplesdating.couplet.ui.ideaList

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentIdeaListBinding
import com.couplesdating.couplet.databinding.FragmentIdeaListBindingImpl
import com.couplesdating.couplet.domain.model.Idea
import com.couplesdating.couplet.ui.extensions.showError
import com.couplesdating.couplet.ui.utils.CircularOutlineProvider
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class IdeaListFragment(
    private val viewModel: IdeaListViewModel
) : Fragment() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.ideas_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.info -> {
                showInfoScreen()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showInfoScreen() {
        val toInfoFragment =
            IdeaListFragmentDirections.actionIdeaListFragmentToInfoFragment()
        findNavController().navigate(toInfoFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    handleUIState(it)
                }
        }
        with(binding) {
            setTitleAndIllustration()
            setButtonsElevation()
            setButtonsListeners()
            setIdeasViewPager()
        }
    }

    private fun FragmentIdeaListBinding.setTitleAndIllustration() {
        illustration.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), category.imageBig)
        )
        title.text = category.title
    }

    private fun FragmentIdeaListBinding.setIdeasViewPager() {
        pager.isSaveEnabled = false
        pager.adapter = IdeaPagerAdapter(
            ideaList = ideas,
            ideaListFragment = this@IdeaListFragment
        )
        pager.isUserInputEnabled = false
    }

    private fun FragmentIdeaListBinding.setButtonsElevation() {
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

    private fun FragmentIdeaListBinding.setButtonsListeners() {
        no.setOnClickListener {
            viewModel.onNoClick(
                categoryId = category.id,
                idea = getCurrentIdea()
            )
        }
        yes.setOnClickListener {
            viewModel.onYesClick(
                categoryId = category.id,
                idea = getCurrentIdea()
            )
        }
        maybe.setOnClickListener {
            viewModel.onMaybeClick(
                categoryId = category.id,
                idea = getCurrentIdea()
            )
        }
    }

    private fun getCurrentIdea(): Idea {
        val currentItemPosition = binding.pager.currentItem
        return ideas[currentItemPosition]
    }

    private fun handleUIState(uiState: IdeaUIState) {
        when (uiState) {
            is IdeaUIState.Loading -> showLoading()
            IdeaUIState.Success -> showNextIdea()
            is IdeaUIState.Error -> {
                binding.loadingContainer.isVisible = false
                showError(uiState.message)
            }
        }
    }

    private fun showLoading() = with(binding) {
        loadingContainer.isVisible = true
    }

    private fun showNextIdea() = with(binding) {
        loadingContainer.isVisible = false
        val currentItem = pager.currentItem
        if (currentItem == ideas.size - 1) {
            val toEmptyList =
                IdeaListFragmentDirections.actionIdeaListFragmentToEmptyListFragment()
            findNavController().navigate(toEmptyList)
        } else {
            pager.currentItem = currentItem + 1
        }
    }
}