package com.couplesdating.couplet.domain.useCase.idea

import com.couplesdating.couplet.domain.model.Idea

interface RemoveIdeaUseCase {
    suspend fun removeIdea(idea: Idea)
}