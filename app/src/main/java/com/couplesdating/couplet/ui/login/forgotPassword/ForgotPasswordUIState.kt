package com.couplesdating.couplet.ui.login.forgotPassword

sealed class ForgotPasswordUIState {
    object EmailEmpty : ForgotPasswordUIState()
    object Success : ForgotPasswordUIState()
    data class ServerError(val errorMessage: String) : ForgotPasswordUIState()
}
