package com.couplesdating.couplet.ui.register.nameAndGender

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.ui.utils.LiveDataEvent
import com.couplesdating.couplet.ui.utils.asLiveDataEvent

class NameAndGenderViewModel : ViewModel() {
    private val _uiState = MutableLiveData<LiveDataEvent<NameAndGenderUIState>>()
    val uiState: LiveData<LiveDataEvent<NameAndGenderUIState>> = _uiState

    fun validate(
        name: String?,
        gender: String?,
        otherGender: String?
    ) {
        when {
            name.isNullOrBlank() -> _uiState.value = NameAndGenderUIState.NameEmpty.asLiveDataEvent
            gender.isNullOrBlank() -> _uiState.value = NameAndGenderUIState.GenderEmpty.asLiveDataEvent
            gender == "Other" && otherGender.isNullOrBlank() ->
                _uiState.value = NameAndGenderUIState.OtherGenderEmpty.asLiveDataEvent
            else -> _uiState.value = NameAndGenderUIState.Valid.asLiveDataEvent
        }
    }
}