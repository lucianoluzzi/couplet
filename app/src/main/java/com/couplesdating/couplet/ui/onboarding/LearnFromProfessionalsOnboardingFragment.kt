package com.couplesdating.couplet.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.couplesdating.couplet.databinding.FragmentLearnFromProfessionalsBinding

class LearnFromProfessionalsOnboardingFragment : Fragment() {
    private lateinit var binding: FragmentLearnFromProfessionalsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLearnFromProfessionalsBinding.inflate(inflater, container, false)
        return binding.root
    }
}