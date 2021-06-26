package com.couplesdating.couplet.ui.ideaList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.idea.IdeaResponseEvents
import com.couplesdating.couplet.domain.model.Idea
import com.couplesdating.couplet.domain.model.UserResponse
import com.couplesdating.couplet.domain.network.Response
import com.couplesdating.couplet.domain.useCase.idea.SendIdeaResponseUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class IdeaListViewModel(
    private val sendIdeaResponseUseCase: SendIdeaResponseUseCase,
    private val analytics: Analytics
) : ViewModel() {
    private val uiStateChannel = Channel<IdeaUIState>(Channel.CONFLATED)
    val uiState = uiStateChannel.receiveAsFlow().distinctUntilChanged()

    fun onYesClick(
        categoryId: String,
        idea: Idea
    ) {
        analytics.trackEvent(
            IdeaResponseEvents.OnYesClicked(
                ideaId = idea.id,
                categoryId = categoryId
            )
        )
        sendResponse(
            ideaId = idea.id,
            userResponse = UserResponse.YES
        )
    }

    fun onNoClick(
        categoryId: String,
        idea: Idea
    ) {
        analytics.trackEvent(
            IdeaResponseEvents.OnNoClicked(
                ideaId = idea.id,
                categoryId = categoryId
            )
        )
        sendResponse(
            ideaId = idea.id,
            userResponse = UserResponse.NO
        )
    }

    fun onMaybeClick(
        categoryId: String,
        idea: Idea
    ) {
        analytics.trackEvent(
            IdeaResponseEvents.OnMaybeClicked(
                ideaId = idea.id,
                categoryId = categoryId
            )
        )
        sendResponse(
            ideaId = idea.id,
            userResponse = UserResponse.MAYBE
        )
    }

    private fun sendResponse(
        ideaId: String,
        userResponse: UserResponse
    ) {
        viewModelScope.launch {
            uiStateChannel.send(IdeaUIState.Loading)
            val response = sendIdeaResponseUseCase.sendIdeaResponse(
                ideaId = ideaId,
                userResponse = userResponse
            )
            emitResponse(response)
        }
    }

    private suspend fun emitResponse(response: Response) {
        when (response) {
            is Response.Success<*>,
            Response.Completed -> uiStateChannel.send(IdeaUIState.Success)
            is Response.Error -> uiStateChannel.send(IdeaUIState.Error(response.errorMessage))
        }
    }
}