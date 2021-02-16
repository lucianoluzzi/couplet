package com.couplesdating.couplet.ui.register.nameAndGender

sealed class NameAndGenderUIState {
    object NameEmpty : NameAndGenderUIState()
    object GenderEmpty : NameAndGenderUIState()
    object OtherGenderEmpty : NameAndGenderUIState()
    object Success : NameAndGenderUIState()
}
