package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.model.InviteModel
import com.couplesdating.couplet.domain.model.Response

interface InviteRepository {
    suspend fun saveInvite(inviteModel: InviteModel): Response
    suspend fun inviteExists(inviteId: String): Boolean
    fun savePairInvite(invite: InviteModel)
    suspend fun getPairInvite(currentUserId: String? = null): InviteModel?
    suspend fun deletePairInvite(inviteId: String)
}