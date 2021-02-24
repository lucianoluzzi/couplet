package com.couplesdating.couplet.ui.login.socialLogin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.domain.useCase.GetCurrentUserUseCase
import com.couplesdating.couplet.domain.useCase.GoogleSignInUseCase
import com.couplesdating.couplet.ui.utils.LiveDataEvent
import com.couplesdating.couplet.ui.utils.asLiveDataEvent
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.launch

class SocialLoginViewModel(
    getCurrentUserUseCase: GetCurrentUserUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase
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
                is Response.Success -> setLiveDataValue(SocialLoginUIState.Success)
                is Response.Error -> setLiveDataValue(
                    SocialLoginUIState.AuthError(
                        response.errorMessage ?: ""
                    )
                )
            }
        }
    }

    private fun setLiveDataValue(uiState: SocialLoginUIState) {
        _uiStateLiveData.value = uiState.asLiveDataEvent
    }
}