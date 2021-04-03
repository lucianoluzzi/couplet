package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.model.Response

interface PairRepository {
    fun saveAcceptedPairInvite(firstUserId: String)
    fun getAcceptedPairInviteUser(): String?
    fun deletePairInvite()
    suspend fun formPair(firstUserId: String, secondUserId: String): Response
}