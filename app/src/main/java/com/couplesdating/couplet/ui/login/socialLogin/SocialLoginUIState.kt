package com.couplesdating.couplet.ui.login.socialLogin

import com.couplesdating.couplet.domain.model.User

sealed class SocialLoginUIState {
    data class Success(val user: User) : SocialLoginUIState()
    data class AuthError(val error: String) : SocialLoginUIState()
    object Loading : SocialLoginUIState()
}
