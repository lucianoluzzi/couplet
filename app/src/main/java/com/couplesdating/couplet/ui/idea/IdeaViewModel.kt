package com.couplesdating.couplet.ui.idea

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.domain.model.Idea
import com.couplesdating.couplet.domain.useCase.idea.DecorateIdeaUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class IdeaViewModel(
    private val decorateIdeaUseCase: DecorateIdeaUseCase
) : ViewModel() {
    private lateinit var idea: Idea
    private val uiStateChannel = Channel<IdeaUIModel>(Channel.CONFLATED)
    val uiState = uiStateChannel.receiveAsFlow().distinctUntilChanged()

    fun init(idea: Idea) {
        this.idea = idea
        getWordsToDecorate()
    }

    private fun getWordsToDecorate() {
        val wordsToDecorate = decorateIdeaUseCase.getIdeasToDecorate(idea.description)
        viewModelScope.launch {
            uiStateChannel.send(
                IdeaUIModel(wordsToDecorate = wordsToDecorate)
            )
        }
    }
}