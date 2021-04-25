package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.model.AcceptedInvite
import com.couplesdating.couplet.domain.model.InviteModel
import com.couplesdating.couplet.domain.model.Response

interface InviteRepository {
    suspend fun createInvite(inviteModel: InviteModel): Response
    suspend fun inviteExists(inviteId: String): Boolean
    fun saveAcceptedPairInvite(acceptedInvite: AcceptedInvite)
    fun getAcceptedPairInviteUser(): AcceptedInvite?
    suspend fun deletePairInvite()
}