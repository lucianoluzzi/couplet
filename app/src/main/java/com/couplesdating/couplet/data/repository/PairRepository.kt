package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.network.Response
import kotlinx.coroutines.flow.Flow

interface PairRepository {
    fun shouldShowSync(): Flow<Boolean>
    fun setSyncPageShown()
    suspend fun formPair(inviterId: String, currentUserId: String): Response
    suspend fun getPartner(currentUserId: String): Response
}