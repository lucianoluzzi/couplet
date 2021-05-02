package com.couplesdating.couplet.domain.useCase.pair

import com.couplesdating.couplet.domain.model.User
import kotlinx.coroutines.flow.Flow

interface ShouldShowSyncUseCase {
    suspend fun invoke(currentUser: User? = null) : Flow<Boolean>
}