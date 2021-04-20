package com.couplesdating.couplet.ui.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.couplesdating.couplet.databinding.FragmentErrorBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ErrorFragment : Fragment() {
    private lateinit var binding: FragmentErrorBinding
    private val viewModel by viewModel<ErrorViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("error")?.let {
            binding.label.text = it
        }

        binding.tryAgain.setOnClickListener {
            viewModel.onTryAgainClicked()
            findNavController().popBackStack()
        }
    }
}