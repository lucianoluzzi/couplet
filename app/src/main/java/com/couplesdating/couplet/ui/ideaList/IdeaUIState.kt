package com.couplesdating.couplet.ui.ideaList

sealed class IdeaUIState {
    object Loading : IdeaUIState()
    data class Error(val message: String? = null) : IdeaUIState()
    object Success : IdeaUIState()
}
