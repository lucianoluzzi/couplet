package com.couplesdating.couplet.ui.register.nameAndGender

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.login.NameAndGenderEvents
import com.couplesdating.couplet.domain.network.Response
import com.couplesdating.couplet.domain.useCase.user.UpdateUserUseCase
import com.couplesdating.couplet.ui.utils.LiveDataEvent
import com.couplesdating.couplet.ui.utils.asLiveDataEvent
import kotlinx.coroutines.launch

class NameAndGenderViewModel(
    private val updateUserUseCase: UpdateUserUseCase,
    private val analytics: Analytics
) : ViewModel() {
    private val _uiState = MutableLiveData<LiveDataEvent<NameAndGenderUIState>>()
    val uiState: LiveData<LiveDataEvent<NameAndGenderUIState>> = _uiState

    fun updateUser(
        name: String?,
        gender: String?,
        otherGender: String?
    ) {
        analytics.trackEvent(NameAndGenderEvents.RegisterClick)
        when {
            name.isNullOrBlank() -> {
                analytics.trackEvent(NameAndGenderEvents.NameEmpty)
                _uiState.value = NameAndGenderUIState.NameEmpty.asLiveDataEvent
            }
            gender.isNullOrBlank() -> {
                analytics.trackEvent(NameAndGenderEvents.GenderEmpty)
                _uiState.value =
                    NameAndGenderUIState.GenderEmpty.asLiveDataEvent
            }
            gender == "Other" && otherGender.isNullOrBlank() -> {
                analytics.trackEvent(NameAndGenderEvents.OtherGenderEmpty)
                _uiState.value = NameAndGenderUIState.OtherGenderEmpty.asLiveDataEvent
            }
            else -> {
                val inputtedGender = if (gender == "Other") {
                    otherGender
                } else {
                    gender
                }
                updateUser(
                    name = name,
                    gender = inputtedGender ?: ""
                )
            }
        }
    }

    private fun updateUser(
        name: String,
        gender: String
    ) {
        viewModelScope.launch {
            val updateResponse = updateUserUseCase.updateNameAndGender(
                name = name,
                gender = gender
            )
            if (updateResponse is Response.Error) {
                analytics.trackEvent(
                    NameAndGenderEvents.UpdateError(updateResponse.errorMessage)
                )
                val errorMessage = updateResponse.errorMessage ?: ""
                _uiState.value =
                    NameAndGenderUIState.UpdateError(errorMessage).asLiveDataEvent
            } else {
                analytics.trackEvent(NameAndGenderEvents.UpdateSuccess)
                _uiState.value = NameAndGenderUIState.Success.asLiveDataEvent
            }
        }
    }

    fun onNameClick() {
        analytics.trackEvent(NameAndGenderEvents.NameInputClick)
    }

    fun onGenderClick() {
        analytics.trackEvent(NameAndGenderEvents.GenderInputClick)
    }

    fun onOtherGenderClick() {
        analytics.trackEvent(NameAndGenderEvents.OtherGenderInputClick)
    }
}