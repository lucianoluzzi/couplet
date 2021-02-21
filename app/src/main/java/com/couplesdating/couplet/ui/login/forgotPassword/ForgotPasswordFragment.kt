package com.couplesdating.couplet.ui.login.forgotPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentForgotPasswordBinding
import com.couplesdating.couplet.ui.extensions.textValue
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    private val viewModel: ForgotPasswordViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        binding.resetPassword.setOnClickListener {
            viewModel.onResetClicked(binding.email.textValue())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                ForgotPasswordUIState.EmailEmpty -> binding.emailInputLayout.error =
                    getString(R.string.empty_login_error)
                is ForgotPasswordUIState.ServerError -> binding.emailInputLayout.error =
                    uiState.errorMessage
                ForgotPasswordUIState.Success -> showConfirmation()
            }
        }
    }

    private fun showConfirmation() {
        Snackbar.make(binding.root, R.string.password_reset, LENGTH_LONG).show()
    }
}