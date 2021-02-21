package com.couplesdating.couplet.ui.login.forgotPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.domain.useCase.ResetPasswordUseCase
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {
    private val _uiState = MutableLiveData<ForgotPasswordUIState>()
    val uiState: LiveData<ForgotPasswordUIState> = _uiState

    fun onResetClicked(email: String?) {
        when {
            email.isNullOrBlank() -> _uiState.value = ForgotPasswordUIState.EmailEmpty
            else -> resetPassword(email)
        }
    }

    private fun resetPassword(email: String) {
        viewModelScope.launch {
            val response = resetPasswordUseCase.resetPassword(email)
            if (response is Response.Error) {
                _uiState.value =
                    ForgotPasswordUIState.ServerError(response.errorMessage ?: "")
            } else {
                _uiState.value = ForgotPasswordUIState.Success
            }
        }
    }
}