package com.couplesdating.couplet.ui.profile

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentProfileBinding
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.textValue
import com.couplesdating.couplet.ui.profile.adapter.SettingsAdapter
import com.couplesdating.couplet.ui.widgets.DividerItemDecorator

class ProfileFragment(
    private val viewModel: ProfileViewModel
) : Fragment() {
    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentProfileBinding.inflate(layoutInflater)
    }
    private val navigationArgs by navArgs<ProfileFragmentArgs>()
    private val user by lazy {
        navigationArgs.user
    }
    private val settingsAdapter = SettingsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        name.text = user.name
        decorateTitle()
        setBackButton()
        binding.settingsList.adapter = settingsAdapter
        val dividerItemDecoration = DividerItemDecorator(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.view_settings_divider
            )
        )
        binding.settingsList.addItemDecoration(dividerItemDecoration)
        viewModel.settingsLiveData.observe(viewLifecycleOwner) { settingsItems ->
            settingsAdapter.submitList(settingsItems)
        }
    }

    private fun setBackButton() = with(binding) {
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun decorateTitle() = with(binding) {
        val titleValue = title.textValue()
        val spannable = SpannableString(titleValue)
        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        val color = requireContext().getColor(R.color.red)
        val wordToDecorate = "Profile"
        spannable.setColor(
            color = color,
            wordToDecorate = wordToDecorate,
            wholeText = titleValue
        )
        spannable.setFont(
            typeface = medium,
            wordToDecorate = wordToDecorate,
            wholeText = titleValue
        )

        title.text = spannable
    }
}