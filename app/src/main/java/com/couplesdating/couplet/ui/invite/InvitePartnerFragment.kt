package com.couplesdating.couplet.ui.invite

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentInvitePartnerBinding
import com.couplesdating.couplet.ui.extensions.onGetFocus
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.textValue
import org.koin.androidx.viewmodel.ext.android.viewModel

class InvitePartnerFragment : Fragment() {
    private lateinit var binding: FragmentInvitePartnerBinding
    private val viewModel by viewModel<InvitePartnerViewModel>()
    private var hasClickedOnShare = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInvitePartnerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        decorateTitle()
        viewModel.deepLink.observe(viewLifecycleOwner) { liveDataEvent ->
            liveDataEvent.getContentIfNotHandled()?.let {
                handleUIState(it)
            }
        }

        with(binding) {
            partner.onGetFocus {
                viewModel.onDisplayNameClicked()
            }
            note.onGetFocus {
                viewModel.onNoteClicked()
            }
            shareLink.setOnClickListener {
                viewModel.createInviteLink(
                    binding.partner.textValue(),
                    binding.note.textValue()
                )
            }
        }
    }

    private fun decorateTitle() {
        val titleText = binding.title.textValue()
        val spannable = SpannableString(titleText)

        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        val color = requireContext().getColor(R.color.red)
        spannable.setColor(color, "partner", titleText)
        spannable.setFont(medium, "partner", titleText)
        binding.title.text = spannable
    }

    private fun handleUIState(uiState: InvitePartnerUIState) {
        when (uiState) {
            is InvitePartnerUIState.Error -> goToError(uiState.errorMessage)
            is InvitePartnerUIState.Success -> shareApp(uiState.inviteUri)
        }
    }

    private fun shareApp(link: String) {
        hasClickedOnShare = true
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, link)

        startActivity(Intent.createChooser(intent, "Share Link"))
    }

    private fun goToError(errorMessage: String? = null) {
        val bundle = errorMessage?.let {
            bundleOf("error" to errorMessage)
        }
        findNavController().navigate(R.id.errorFragment, bundle)
    }

    override fun onResume() {
        super.onResume()
        if (hasClickedOnShare) {
            findNavController().popBackStack()
        }
    }
}