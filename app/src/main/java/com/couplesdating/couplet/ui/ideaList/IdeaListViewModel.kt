package com.couplesdating.couplet.ui.ideaList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.idea.IdeaResponseEvents
import com.couplesdating.couplet.domain.model.Idea
import com.couplesdating.couplet.domain.model.UserResponse
import com.couplesdating.couplet.domain.network.Response
import com.couplesdating.couplet.domain.useCase.idea.DecorateIdeaUseCase
import com.couplesdating.couplet.domain.useCase.idea.RemoveIdeaUseCase
import com.couplesdating.couplet.domain.useCase.idea.SendIdeaResponseUseCase
import com.couplesdating.couplet.ui.extensions.doNothing
import kotlinx.coroutines.launch

class IdeaListViewModel(
    private val sendIdeaResponseUseCase: SendIdeaResponseUseCase,
    private val removeIdeaUseCase: RemoveIdeaUseCase,
    private val decorateIdeaUseCase: DecorateIdeaUseCase,
    private val analytics: Analytics
) : ViewModel() {

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
            idea = idea,
            userResponse = UserResponse.YES
        )
        removeIdea(
            idea = idea
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
            idea = idea,
            userResponse = UserResponse.NO
        )
        removeIdea(
            idea = idea
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
            idea = idea,
            userResponse = UserResponse.MAYBE
        )
        removeIdea(
            idea = idea
        )
    }

    private fun sendResponse(
        idea: Idea,
        userResponse: UserResponse
    ) {
        viewModelScope.launch {
            val response = sendIdeaResponseUseCase.sendIdeaResponse(
                idea = idea,
                userResponse = userResponse
            )
            deleteIdeaIfSuccess(
                response = response,
                idea = idea
            )
        }
    }

    private fun removeIdea(idea: Idea) {
        viewModelScope.launch {
            removeIdeaUseCase.removeIdea(
                idea = idea
            )
        }
    }

    private suspend fun deleteIdeaIfSuccess(response: Response, idea: Idea) {
        when (response) {
            is Response.Success<*>,
            Response.Completed -> removeIdeaUseCase.removeIdea(idea)
            is Response.Error -> doNothing
        }
    }

    fun getWordsToDecorateInTitle(title: String): List<String> =
        decorateIdeaUseCase.getIdeasToDecorate(title)
}