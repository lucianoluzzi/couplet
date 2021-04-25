package com.couplesdating.couplet.domain.useCase.pair

import kotlinx.coroutines.flow.Flow

interface ShouldShowSyncUseCase {
    fun invoke() : Flow<Boolean>
}