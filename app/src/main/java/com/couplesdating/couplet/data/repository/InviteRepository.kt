package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.model.InviteModel
import com.couplesdating.couplet.domain.network.Response

interface InviteRepository {
    suspend fun saveInvite(inviteModel: InviteModel): Response
    suspend fun inviteExists(inviteId: String): Boolean
    fun savePairInvite(invite: InviteModel)
    suspend fun getSentPairInvite(currentUserId: String): InviteModel?
    suspend fun getReceivedPairInvite(currentUserId: String? = null): InviteModel?
    suspend fun deletePairInvite(inviteId: String)
}