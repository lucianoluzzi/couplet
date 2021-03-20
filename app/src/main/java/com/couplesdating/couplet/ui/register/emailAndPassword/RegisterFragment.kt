package com.couplesdating.couplet.ui.register.emailAndPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentEmailPasswordBinding
import com.couplesdating.couplet.ui.extensions.textValue
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentEmailPasswordBinding
    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmailPasswordBinding.inflate(inflater, container, false)
        with(binding) {
            register.setOnClickListener {
                clearFormErrors()
                viewModel.validate(
                    email = email.textValue(),
                    password = password.textValue(),
                    confirmPassword = confirmPassword.textValue()
                )
            }
        }

        return binding.root
    }

    private fun clearFormErrors() {
        with(binding) {
            emailInputLayout.error = null
            passwordInputLayout.error = null
            confirmPasswordInputLayout.error = null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.emailScreenUIState.observe(viewLifecycleOwner) { liveDataEvent ->
            binding.loadingContainer.isVisible = false
            when (val registerResult = liveDataEvent.getContentIfNotHandled()) {
                EmailScreenUIState.Loading -> binding.loadingContainer.isVisible = true
                EmailScreenUIState.ConfirmPasswordEmpty -> setError(
                    binding.confirmPasswordInputLayout,
                    getString(R.string.empty_login_error)
                )
                EmailScreenUIState.EmailEmpty -> setError(
                    binding.emailInputLayout,
                    getString(R.string.empty_login_error)
                )
                EmailScreenUIState.PasswordEmpty -> setError(
                    binding.passwordInputLayout,
                    getString(R.string.empty_login_error)
                )
                EmailScreenUIState.PasswordsDoesntMatch -> setError(
                    binding.confirmPasswordInputLayout,
                    getString(R.string.password_dont_match)
                )
                is EmailScreenUIState.RegistrationError -> setError(
                    binding.passwordInputLayout,
                    registerResult.errorMessage
                )
                EmailScreenUIState.Success -> goToNameAndGender()
            }
        }
    }

    private fun setError(textInput: TextInputLayout, errorMessage: String) {
        textInput.error = errorMessage
    }

    private fun goToNameAndGender() {
        val navigationAction =
            EmailAndPasswordFragmentDirections.actionEmailAndPasswordFragmentToNameAndGenderFragment()
        findNavController().navigate(navigationAction)
    }
}