package com.couplesdating.couplet.ui.login.socialLogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.LoginWithEmailCliked
import com.couplesdating.couplet.analytics.events.LoginWithFacebookClicked
import com.couplesdating.couplet.analytics.events.LoginWithGoogleClicked
import com.couplesdating.couplet.analytics.events.RegisterClicked
import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.domain.useCase.FacebookSignInUseCase
import com.couplesdating.couplet.domain.useCase.GetCurrentUserUseCase
import com.couplesdating.couplet.domain.useCase.GoogleSignInUseCase
import com.couplesdating.couplet.ui.utils.LiveDataEvent
import com.couplesdating.couplet.ui.utils.asLiveDataEvent
import com.facebook.AccessToken
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.launch

class SocialLoginViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val facebookSignInUseCase: FacebookSignInUseCase,
    private val analytics: Analytics
) : ViewModel() {

    private val _userLiveData = MutableLiveData<User?>()
    val userLiveData: LiveData<User?> = _userLiveData

    private val _uiStateLiveData = MutableLiveData<LiveDataEvent<SocialLoginUIState>>()
    val uiStateLiveData: LiveData<LiveDataEvent<SocialLoginUIState>> = _uiStateLiveData

    init {
        _userLiveData.value = getCurrentUserUseCase.getCurrentUser()
    }

    fun onGoogleSignIn(credential: AuthCredential, displayName: String) {
        viewModelScope.launch {
            val response = googleSignInUseCase.signIn(
                authCredential = credential,
                displayName = displayName
            )

            when (response) {
                is Response.Success -> {
                    val loggedInUser = getCurrentUserUseCase.getCurrentUser()
                    loggedInUser?.let {
                        setLiveDataValue(
                            SocialLoginUIState.Success(
                                it
                            )
                        )
                    }
                }
                is Response.Error -> setLiveDataValue(
                    SocialLoginUIState.AuthError(
                        response.errorMessage ?: ""
                    )
                )
            }
        }
    }

    fun onFacebookSignIn(facebookAccessToken: AccessToken) {
        viewModelScope.launch {
            when (val response = facebookSignInUseCase.signIn(facebookAccessToken)) {
                is Response.Error -> onResponseError(response)
                is Response.Success -> onSuccessResponse()
            }
        }
    }

    private fun onResponseError(errorResponse: Response.Error) {
        setLiveDataValue(
            SocialLoginUIState.AuthError(
                errorResponse.errorMessage ?: ""
            )
        )
    }

    private fun onSuccessResponse() {
        val loggedInUser = getCurrentUserUseCase.getCurrentUser()
        loggedInUser?.let {
            setLiveDataValue(
                SocialLoginUIState.Success(
                    it
                )
            )
        }
    }

    fun onLoginWithEmailClick() {
        analytics.trackEvent(LoginWithEmailCliked)
    }

    fun onLoginWithGoogleClick() {
        analytics.trackEvent(LoginWithGoogleClicked)
    }

    fun onLoginWithFacebookClicked() {
        analytics.trackEvent(LoginWithFacebookClicked)
    }

    fun onRegisterClicked() {
        analytics.trackEvent(RegisterClicked)
    }

    private fun setLiveDataValue(uiState: SocialLoginUIState) {
        _uiStateLiveData.value = uiState.asLiveDataEvent
    }
}