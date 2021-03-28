package com.couplesdating.couplet.ui.invite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.couplesdating.couplet.databinding.FragmentInvitePartnerBinding
import com.couplesdating.couplet.ui.extensions.textValue
import org.koin.android.viewmodel.ext.android.viewModel

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
                shareApp(it)
            }
        }

        binding.shareLink.setOnClickListener {
            viewModel.createInviteLink(
                binding.note.textValue()
            )
        }
    }

    private fun shareApp(link: Uri) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, link.toString())

        startActivity(Intent.createChooser(intent, "Share Link"))
    }
}