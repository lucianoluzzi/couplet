package com.couplesdating.couplet.ui.login.forgotPassword

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.FragmentForgotPasswordBinding
import com.couplesdating.couplet.ui.extensions.onGetFocus
import com.couplesdating.couplet.ui.extensions.setColor
import com.couplesdating.couplet.ui.extensions.setFont
import com.couplesdating.couplet.ui.extensions.textValue
import com.google.android.material.snackbar.BaseTransientBottomBar
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
        binding.sendLink.setOnClickListener {
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
        decorateTitle()
        setEmailInputAnalyticsTrack()
    }

    private fun showConfirmation() {
        Snackbar
            .make(binding.root, R.string.password_reset, LENGTH_LONG)
            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    if (event == Snackbar.Callback.DISMISS_EVENT_SWIPE) {
                        viewModel.onSnackbarDismissed()
                    }
                }
            })
            .show()
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
            wordToDecorate = "reset",
            titleText
        )
        spannable.setFont(
            typeface = medium,
            wordToDecorate = "reset",
            wholeText = titleText
        )
        binding.title.text = spannable
    }

    private fun setEmailInputAnalyticsTrack() {
        binding.email.onGetFocus {
            viewModel.onEmailInputClicked()
        }
    }
}