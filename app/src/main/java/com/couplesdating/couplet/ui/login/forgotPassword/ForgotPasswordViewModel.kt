package com.couplesdating.couplet.ui.login.forgotPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.login.ForgotPasswordEvents
import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.domain.useCase.auth.ResetPasswordUseCase
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val analitycs: Analytics
) : ViewModel() {
    private val _uiState = MutableLiveData<ForgotPasswordUIState>()
    val uiState: LiveData<ForgotPasswordUIState> = _uiState

    fun onResetClicked(email: String?) {
        analitycs.trackEvent(ForgotPasswordEvents.SendLinkClicked)
        when {
            email.isNullOrBlank() -> {
                analitycs.trackEvent(ForgotPasswordEvents.EmailEmptyState)
                _uiState.value = ForgotPasswordUIState.EmailEmpty
            }
            else -> resetPassword(email)
        }
    }

    private fun resetPassword(email: String) {
        viewModelScope.launch {
            val response = resetPasswordUseCase.resetPassword(email)
            if (response is Response.Error) {
                analitycs.trackEvent(ForgotPasswordEvents.SendLinkError(response.errorMessage))
                _uiState.value =
                    ForgotPasswordUIState.ServerError(response.errorMessage ?: "")
            } else {
                analitycs.trackEvent(ForgotPasswordEvents.ResetLinkSent)
                _uiState.value = ForgotPasswordUIState.Success
            }
        }
    }

    fun onSnackbarDismissed() {
        analitycs.trackEvent(ForgotPasswordEvents.SnackBarDismissed)
    }

    fun onEmailInputClicked() {
        analitycs.trackEvent(ForgotPasswordEvents.EmailInputClicked)
    }
}