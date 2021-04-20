package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.ui.invite.InviteModel

interface PairRepository {
    fun saveAcceptedPairInvite(firstUserId: String)
    fun getAcceptedPairInviteUser(): String?
    fun deletePairInvite()
    fun shouldShowSync(): Boolean
    fun setSyncPageShown()
    suspend fun formPair(firstUserId: String, secondUserId: String): Response
    suspend fun createInvite(inviteModel: InviteModel): Response
}