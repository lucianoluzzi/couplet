package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.model.AcceptedInvite
import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.domain.model.InviteModel
import kotlinx.coroutines.flow.Flow

interface PairRepository {
    fun saveAcceptedPairInvite(acceptedInvite: AcceptedInvite)
    fun getAcceptedPairInviteUser(): AcceptedInvite?
    fun deletePairInvite()
    fun shouldShowSync(): Flow<Boolean>
    fun setSyncPageShown()
    suspend fun formPair(firstUserId: String, secondUserId: String): Response
    suspend fun createInvite(inviteModel: InviteModel): Response
    suspend fun getPartner(currentUserId: String): Response
    suspend fun inviteExists(inviteId: String): Boolean
}