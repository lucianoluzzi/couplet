package com.couplesdating.couplet.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentEmailPasswordBinding
import com.couplesdating.couplet.ui.extensions.textValue
import org.koin.android.viewmodel.ext.android.sharedViewModel

class EmailAndPasswordFragment : Fragment() {
    private lateinit var binding: FragmentEmailPasswordBinding
    private val viewModel: RegisterViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmailPasswordBinding.inflate(inflater, container, false)
        with(binding) {
            register.setOnClickListener {
                clearFormErrors()
                viewModel.setEmailAndPassword(
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
            when (liveDataEvent.getContentIfNotHandled()) {
                EmailScreenUIState.ConfirmPasswordEmpty -> binding.confirmPasswordInputLayout.error =
                    getString(R.string.empty_login_error)
                EmailScreenUIState.EmailEmpty -> binding.emailInputLayout.error =
                    getString(R.string.empty_login_error)
                EmailScreenUIState.PasswordEmpty -> binding.passwordInputLayout.error =
                    getString(R.string.empty_login_error)
                EmailScreenUIState.PasswordsDoesntMatch -> binding.confirmPasswordInputLayout.error =
                    getString(R.string.password_dont_match)
                EmailScreenUIState.Success -> goToNameAndGender()
            }
        }
    }

    private fun goToNameAndGender() {
        val navigationAction =
            EmailAndPasswordFragmentDirections.actionEmailAndPasswordFragmentToNameAndGenderFragment()
        findNavController().navigate(navigationAction)
    }
}