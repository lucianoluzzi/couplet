package com.couplesdating.couplet.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentLoginBinding
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.ui.extensions.textValue
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

    private fun handleUIState(uiState: LoginUIState) {
        when (uiState) {
            is LoginUIState.EmailEmpty -> binding.emailInputLayout.error =
                getString(R.string.empty_login_error)
            is LoginUIState.PasswordEmpty -> binding.passwordInputLayout.error =
                getString(R.string.empty_login_error)
            LoginUIState.AuthError -> binding.passwordInputLayout.error =
                getString(R.string.auth_login_error)
            LoginUIState.Loading -> TODO()
            LoginUIState.Success -> TODO()
        }
    }

    private fun clearErrors() {
        binding.emailInputLayout.error = null
        binding.passwordInputLayout.error = null
    }

    private fun getUser(): User? {
        binding.emailInputLayout.error = null
        binding.passwordInputLayout.error = null
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        if (email.isEmpty()) {
            binding.emailInputLayout.error = getString(R.string.empty_login_error)
            return null
        }
        if (password.isEmpty()) {
            binding.passwordInputLayout.error = getString(R.string.empty_login_error)
            return null
        }

        return User(
            email = email,
            password = password
        )
    }
}