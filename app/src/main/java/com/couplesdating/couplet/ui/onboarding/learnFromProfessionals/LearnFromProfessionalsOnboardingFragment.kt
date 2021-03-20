package com.couplesdating.couplet.ui.onboarding.learnFromProfessionals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.couplesdating.couplet.databinding.FragmentLearnFromProfessionalsBinding
import org.koin.android.viewmodel.ext.android.viewModel

class LearnFromProfessionalsOnboardingFragment : Fragment() {
    private lateinit var binding: FragmentLearnFromProfessionalsBinding
    private val viewModel by viewModel<LearnFromProfessionalsViewModel>()

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
            viewModel.onContinueClicked()
            val toWildOnboardingFragment =
                LearnFromProfessionalsOnboardingFragmentDirections.actionLearnFromProfessionalsFragmentToMildToWildOnboardingFragment()
            findNavController().navigate(toWildOnboardingFragment)
        }
    }
}