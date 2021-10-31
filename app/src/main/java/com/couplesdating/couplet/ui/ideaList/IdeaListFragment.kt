package com.couplesdating.couplet.ui.ideaList

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentIdeaListBinding
import com.couplesdating.couplet.databinding.FragmentIdeaListBindingImpl
import com.couplesdating.couplet.domain.model.Idea
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.textValue
import com.couplesdating.couplet.ui.utils.CircularOutlineProvider
import com.couplesdating.couplet.ui.widgets.ViewPagerViewHeightAnimator

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
        decorateTitle()
    }

    private fun decorateTitle() {
        val title = binding.title.textValue()
        val spannable = SpannableString(title)

        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        val color = requireContext().getColor(R.color.red)
        viewModel.getWordsToDecorateInTitle(category.title).forEach { word ->
            spannable.setFont(
                typeface = medium,
                wordToDecorate = word,
                wholeText = title
            )
            spannable.setColor(
                color = color,
                wordToDecorate = word,
                wholeText = title
            )
        }
        binding.title.text = spannable
    }

    private fun FragmentIdeaListBinding.setIdeasViewPager() {
        pager.isSaveEnabled = false
        val ideaPagerAdapter = IdeaPagerAdapter(
            ideaList = ideas,
            ideaListFragment = this@IdeaListFragment
        )
        pager.adapter = ideaPagerAdapter
        pager.isUserInputEnabled = false
        val viewPager2ViewHeightAnimator = ViewPagerViewHeightAnimator()
        viewPager2ViewHeightAnimator.viewPager2 = pager
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
            showNextIdea()
        }
        yes.setOnClickListener {
            viewModel.onYesClick(
                categoryId = category.id,
                idea = getCurrentIdea()
            )
            showNextIdea()
        }
        maybe.setOnClickListener {
            viewModel.onMaybeClick(
                categoryId = category.id,
                idea = getCurrentIdea()
            )
            showNextIdea()
        }
    }

    private fun getCurrentIdea(): Idea {
        val currentItemPosition = binding.pager.currentItem
        return ideas[currentItemPosition]
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