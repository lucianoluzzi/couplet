package com.couplesdating.couplet.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.continueButton.setOnClickListener {
            val toWildOnboardingFragment =
                LearnFromProfessionalsOnboardingFragmentDirections.actionLearnFromProfessionalsFragmentToMildToWildOnboardingFragment()
            findNavController().navigate(toWildOnboardingFragment)
        }
    }
}