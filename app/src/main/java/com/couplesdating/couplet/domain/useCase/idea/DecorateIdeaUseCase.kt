package com.couplesdating.couplet.domain.useCase.idea

interface DecorateIdeaUseCase {
    fun getIdeasToDecorate(description: String): List<String>
}