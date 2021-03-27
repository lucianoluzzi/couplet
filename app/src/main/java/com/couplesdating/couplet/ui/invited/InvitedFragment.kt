package com.couplesdating.couplet.ui.invited

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.couplesdating.couplet.databinding.FragmentInvitedBinding

class InvitedFragment : Fragment() {
    private lateinit var binding: FragmentInvitedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInvitedBinding.inflate(inflater, container, false)
        return binding.root
    }
}