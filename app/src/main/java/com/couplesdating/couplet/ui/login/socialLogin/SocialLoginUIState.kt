package com.couplesdating.couplet.ui.login.socialLogin

sealed class SocialLoginUIState {
    object Success : SocialLoginUIState()
    data class AuthError(val error: String) : SocialLoginUIState()
    object Loading : SocialLoginUIState()
}
