package com.couplesdating.couplet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.couplesdating.couplet.databinding.FragmentLoginBinding
import com.couplesdating.couplet.ui.viewModel.LoginViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }
}