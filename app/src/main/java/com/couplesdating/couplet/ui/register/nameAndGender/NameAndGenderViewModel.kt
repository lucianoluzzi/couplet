package com.couplesdating.couplet.ui.register.nameAndGender

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.couplesdating.couplet.ui.utils.LiveDataEvent

class NameAndGenderViewModel : ViewModel() {
    private val _uiState = MutableLiveData<LiveDataEvent<NameAndGenderUIState>>()
    val uiState: LiveData<LiveDataEvent<NameAndGenderUIState>> = _uiState

    
}