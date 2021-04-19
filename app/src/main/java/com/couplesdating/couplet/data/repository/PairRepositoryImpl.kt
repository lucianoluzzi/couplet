package com.couplesdating.couplet.data.repository

import android.content.SharedPreferences
import com.couplesdating.couplet.data.extensions.insert
import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.ui.invite.InviteModel
import com.google.firebase.firestore.FirebaseFirestore

class PairRepositoryImpl(
    private val database: FirebaseFirestore,
    private val preferences: SharedPreferences
) : PairRepository {

    override fun saveAcceptedPairInvite(firstUserId: String) {
        with(preferences.edit()) {
            putString(ACCEPTED_PAIR_INVITE_KEY, firstUserId)
            apply()
        }
    }

    override fun getAcceptedPairInviteUser(): String? =
        preferences.getString(ACCEPTED_PAIR_INVITE_KEY, null)

    override fun deletePairInvite() {
        with(preferences.edit()) {
            putString(ACCEPTED_PAIR_INVITE_KEY, null)
            apply()
        }
    }

    override suspend fun formPair(
        firstUserId: String,
        secondUserId: String
    ): Response {
        val pairMap = hashMapOf(
            "user_1" to firstUserId,
            "user_2" to secondUserId
        )

        val taskResponse = database
            .collection("pair")
            .insert(pairMap)

        if (taskResponse.isSuccessful) {
            return Response.Completed
        }

        return Response.Error(taskResponse.exception?.message)
    }

    override suspend fun createInvite(inviteModel: InviteModel): Response {
        val pairMap = hashMapOf(
            "user_1" to inviteModel.userId,
            "invite_id" to inviteModel.inviteId,
            "display_name" to inviteModel.displayName,
            "inviter_name" to inviteModel.currentUserDisplay,
            "note" to inviteModel.note
        )

        val taskResponse = database
            .collection("invite")
            .insert(pairMap)

        if (taskResponse.isSuccessful) {
            return Response.Completed
        }

        return Response.Error(taskResponse.exception?.message)
    }

    private companion object {
        private const val ACCEPTED_PAIR_INVITE_KEY = "ACCEPTED_PAIR_INVITE"
    }
}