package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.network.Response
import kotlinx.coroutines.flow.Flow

interface PairRepository {
    fun shouldShowSync(): Flow<Boolean>
    fun setSyncPageShown()
    suspend fun formPair(firstUserId: String, secondUserId: String): Response
    suspend fun getPartner(currentUserId: String): Response
}