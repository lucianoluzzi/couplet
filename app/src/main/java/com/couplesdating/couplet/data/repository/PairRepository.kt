package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.ui.invite.InviteModel
import kotlinx.coroutines.flow.Flow

interface PairRepository {
    fun saveAcceptedPairInvite(firstUserId: String)
    fun getAcceptedPairInviteUser(): String?
    fun deletePairInvite()
    fun shouldShowSync(): Flow<Boolean>
    fun setSyncPageShown()
    suspend fun formPair(firstUserId: String, secondUserId: String): Response
    suspend fun createInvite(inviteModel: InviteModel): Response
    suspend fun getPartner(currentUserId: String): Response
}