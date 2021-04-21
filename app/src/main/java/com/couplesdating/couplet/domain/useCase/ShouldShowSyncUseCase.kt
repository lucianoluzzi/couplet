package com.couplesdating.couplet.domain.useCase

import kotlinx.coroutines.flow.Flow

interface ShouldShowSyncUseCase {
    fun invoke() : Flow<Boolean>
}