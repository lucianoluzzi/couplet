package com.couplesdating.couplet.domain.useCase.invite

import android.util.Log
import com.couplesdating.couplet.data.repository.InviteRepository
import com.couplesdating.couplet.domain.model.InviteModel
import com.google.firebase.crashlytics.FirebaseCrashlytics

class GetSentPairInviteUseCaseImpl(
    private val inviteRepository: InviteRepository
) : GetSentPairInviteUseCase {

    override suspend fun getSentPairInvite(currentUserId: String): InviteModel? {
        return try {
            inviteRepository.getSentPairInvite(currentUserId)
        } catch (exception: Exception) {
            Log.e("GetSentPairInviteUseCaseImpl", exception.message ?: exception.toString())
            FirebaseCrashlytics.getInstance().recordException(exception)
            null
        }
    }
}