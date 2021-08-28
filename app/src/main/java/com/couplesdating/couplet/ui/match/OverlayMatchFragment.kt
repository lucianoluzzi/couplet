package com.couplesdating.couplet.ui.match

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.couplesdating.couplet.databinding.FragmentOverlayMatchBinding
import com.couplesdating.couplet.notifications.FirebaseNotificationService.Companion.MESSAGE_LABEL
import com.couplesdating.couplet.notifications.FirebaseNotificationService.Companion.NOTIFICATION_MESSAGE

class OverlayMatchFragment : Fragment() {

    private val binding by lazy {
        val layoutInflater = LayoutInflater.from(requireContext())
        FragmentOverlayMatchBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.continueButton.setOnClickListener {
            requireActivity().finish()
        }
        requireArguments().getString(NOTIFICATION_MESSAGE)?.let {
            binding.matchText.text = it
        }
        requireArguments().getString(MESSAGE_LABEL)?.let {
            binding.ideaDescription.text = it
        }
    }
}