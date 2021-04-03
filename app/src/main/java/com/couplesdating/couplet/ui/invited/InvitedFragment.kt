package com.couplesdating.couplet.ui.invited

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.couplesdating.couplet.databinding.FragmentInvitedBinding
import org.koin.android.viewmodel.ext.android.viewModel

class InvitedFragment : Fragment() {
    private lateinit var binding: FragmentInvitedBinding
    private val navigationArguments by navArgs<InvitedFragmentArgs>()
    private val viewModel by viewModel<InvitedViewModel>()

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

        viewModel.uiState.observe(viewLifecycleOwner) {
            onUiState(it)
        }
        setUpTexts()
        setUpButtonsClick()
    }

    private fun onUiState(uiState: InvitedUIState) {
        when (uiState) {
            is InvitedUIState.AcceptedInvite -> navigateToSocialLogin()
            is InvitedUIState.RejectedInvite -> navigateToSocialLogin()
        }
    }

    private fun navigateToSocialLogin() {
        val toSocialLogin =
            InvitedFragmentDirections.actionInvitedFragmentToSocialLoginFragment()
        findNavController().navigate(toSocialLogin)
    }

    private fun setUpTexts() {
        binding.invited.text = "You got invited"
        val displayName = navigationArguments.displayName
        if (!displayName.isNullOrBlank()) {
            binding.invited.append(" by $displayName")
        }

        navigationArguments.note?.let {
            binding.note.isVisible = true
            binding.note.text = it
        }
    }

    private fun setUpButtonsClick() {
        binding.accept.setOnClickListener {
            val inviterId = navigationArguments.id
            viewModel.onInviteAccepted(inviterId)
        }
        binding.reject.setOnClickListener {
            viewModel.onInviteRejected()
        }
    }
}