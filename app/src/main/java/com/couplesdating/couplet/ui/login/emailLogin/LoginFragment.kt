package com.couplesdating.couplet.ui.login.emailLogin

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentLoginBinding
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.setUnderline
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
        binding.forgotPassword.setOnClickListener {
            val goToForgotPassword =
                LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
            findNavController().navigate(goToForgotPassword)
        }

        decorateTexts()
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

    private fun decorateTexts() {
        decorateTitle()
        decorateForgotPassword()
    }

    private fun decorateTitle() {
        val titleText = binding.title.textValue()
        val spannable = SpannableString(titleText)

        val color = requireContext().getColor(R.color.red)
        val medium = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.medium),
            Typeface.NORMAL
        )
        spannable.setColor(
            color = color,
            wordToDecorate = "e-mail",
            titleText
        )
        spannable.setFont(
            typeface = medium,
            wordToDecorate = "e-mail",
            wholeText = titleText
        )
        binding.title.text = spannable
    }

    private fun decorateForgotPassword() {
        val forgotPasswordText = binding.forgotPassword.textValue()
        val spannable = SpannableString(forgotPasswordText)
        spannable.setUnderline(
            wordToDecorate = forgotPasswordText,
            wholeText = forgotPasswordText
        )
        binding.forgotPassword.text = spannable
    }
}