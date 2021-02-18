package com.couplesdating.couplet.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.domain.useCase.GetCurrentUserUseCase
import com.couplesdating.couplet.domain.useCase.RegisterUseCase
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {
    private var name = ""
    private var email = ""
    private var password = ""
    private var gender = ""

    private val _userRegisterLiveData = MutableLiveData<User?>()
    val userRegisterLiveData: LiveData<User?> = _userRegisterLiveData

    fun setEmailAndPassword(
        email: String,
        password: String
    ) {
        this.email = email
        this.password = password
    }

    fun setNameAndGender(
        name: String,
        gender: String
    ) {
        this.name = name
        this.gender = gender
    }

    fun register() {
        viewModelScope.launch {
            registerUseCase.register(
                email = email,
                name = name,
                password = password,
                gender = gender
            )

            val currentUser = getCurrentUserUseCase.getCurrentUser()
            _userRegisterLiveData.value = currentUser
        }
    }
}