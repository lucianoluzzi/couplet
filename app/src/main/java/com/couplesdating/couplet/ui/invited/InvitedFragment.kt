package com.couplesdating.couplet.ui.invited

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.couplesdating.couplet.databinding.FragmentInvitedBinding

class InvitedFragment : Fragment() {
    private lateinit var binding: FragmentInvitedBinding
    private val navigationArguments by navArgs<InvitedFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInvitedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inviteeId = navigationArguments.id
        binding.invited.text = "You got invited by $inviteeId"
        navigationArguments.note?.let {
            binding.note.isVisible = true
            binding.note.text = it
        }
    }
}