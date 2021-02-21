package com.couplesdating.couplet.ui.register.nameAndGender

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.domain.useCase.UpdateUserUseCase
import com.couplesdating.couplet.ui.utils.LiveDataEvent
import com.couplesdating.couplet.ui.utils.asLiveDataEvent
import kotlinx.coroutines.launch

class NameAndGenderViewModel(
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {
    private val _uiState = MutableLiveData<LiveDataEvent<NameAndGenderUIState>>()
    val uiState: LiveData<LiveDataEvent<NameAndGenderUIState>> = _uiState

    fun updateUser(
        name: String?,
        gender: String?,
        otherGender: String?
    ) {
        when {
            name.isNullOrBlank() -> _uiState.value = NameAndGenderUIState.NameEmpty.asLiveDataEvent
            gender.isNullOrBlank() -> _uiState.value =
                NameAndGenderUIState.GenderEmpty.asLiveDataEvent
            gender == "Other" && otherGender.isNullOrBlank() ->
                _uiState.value = NameAndGenderUIState.OtherGenderEmpty.asLiveDataEvent
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
                val errorMessage = updateResponse.errorMessage ?: ""
                _uiState.value =
                    NameAndGenderUIState.UpdateError(errorMessage).asLiveDataEvent
            } else {
                _uiState.value = NameAndGenderUIState.Success.asLiveDataEvent
            }
        }
    }
}