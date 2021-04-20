package com.couplesdating.couplet.ui.invite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentInvitePartnerBinding
import com.couplesdating.couplet.ui.extensions.textValue
import org.koin.androidx.viewmodel.ext.android.viewModel

class InvitePartnerFragment : Fragment() {
    private lateinit var binding: FragmentInvitePartnerBinding
    private val viewModel by viewModel<InvitePartnerViewModel>()

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
        viewModel.deepLink.observe(viewLifecycleOwner) { liveDataEvent ->
            liveDataEvent.getContentIfNotHandled()?.let {
                handleUIState(it)
            }
        }

        binding.shareLink.setOnClickListener {
            viewModel.createInviteLink(
                binding.partner.textValue(),
                binding.note.textValue()
            )
        }
    }

    private fun handleUIState(uiState: InvitePartnerUIState) {
        when (uiState) {
            is InvitePartnerUIState.Error -> goToError(uiState.errorMessage)
            is InvitePartnerUIState.Success -> shareApp(uiState.inviteUri)
        }
    }

    private fun shareApp(link: String) {
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
}