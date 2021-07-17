package com.couplesdating.couplet.ui.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.couplesdating.couplet.databinding.FragmentOverlayMatchBinding

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
}