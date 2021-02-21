package com.couplesdating.couplet.ui.login.emailLogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentLoginBinding
import com.couplesdating.couplet.ui.extensions.textValue
import com.google.android.material.textfield.TextInputLayout
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.uiStateLiveData.observe(viewLifecycleOwner) {
            handleUIState(it)
        }

        binding.login.setOnClickListener {
            clearErrors()
            viewModel.onLoginClicked(
                email = binding.email.textValue(),
                password = binding.password.textValue()
            )
        }
    }

    private fun clearErrors() {
        binding.emailInputLayout.error = null
        binding.passwordInputLayout.error = null
    }

    private fun handleUIState(uiState: LoginUIState) {
        binding.loadingContainer.isVisible = false

        val requireFieldMessage = getString(R.string.empty_login_error)
        when (uiState) {
            is LoginUIState.EmailEmpty -> setError(binding.emailInputLayout, requireFieldMessage)
            is LoginUIState.PasswordEmpty -> setError(
                binding.passwordInputLayout,
                requireFieldMessage
            )
            LoginUIState.AuthError -> setError(
                binding.passwordInputLayout,
                getString(R.string.auth_login_error)
            )
            LoginUIState.Loading -> binding.loadingContainer.isVisible = true
            LoginUIState.Success -> goToSyncWithPartner()
        }
    }

    private fun setError(textInput: TextInputLayout, errorMessage: String) {
        textInput.error = errorMessage
    }

    private fun goToSyncWithPartner() {
        val goToSyncPartner = LoginFragmentDirections.actionLoginFragmentToSyncPartnerFragment()
        findNavController().navigate(goToSyncPartner)
    }
}