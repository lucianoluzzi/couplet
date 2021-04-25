package com.couplesdating.couplet.data.repository

import android.content.SharedPreferences
import com.couplesdating.couplet.domain.model.AcceptedInvite
import com.couplesdating.couplet.domain.model.InviteModel
import com.couplesdating.couplet.domain.model.Response
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await

class InviteRepositoryImpl(
    private val database: FirebaseFirestore,
    private val preferences: SharedPreferences
) : InviteRepository {

    override fun saveAcceptedPairInvite(acceptedInvite: AcceptedInvite) {
        val acceptedInviteJson = Gson().toJson(acceptedInvite)
        with(preferences.edit()) {
            putString(ACCEPTED_PAIR_INVITE_KEY, acceptedInviteJson)
            apply()
        }
    }

    override fun getAcceptedPairInviteUser(): AcceptedInvite? {
        val acceptedInviteJson = preferences.getString(ACCEPTED_PAIR_INVITE_KEY, null)
        return acceptedInviteJson?.let {
            Gson().fromJson(it, AcceptedInvite::class.java)
        }
    }

    override suspend fun deletePairInvite(inviteId: String) {
        database.collection("invite")
            .document(inviteId)
            .delete()
            .await()

        with(preferences.edit()) {
            putString(ACCEPTED_PAIR_INVITE_KEY, null)
            apply()
        }
    }

    override suspend fun createInvite(inviteModel: InviteModel): Response {
        val pairMap = hashMapOf(
            "user_1" to inviteModel.userId,
            "invite_id" to inviteModel.inviteId,
            "display_name" to inviteModel.displayName,
            "inviter_name" to inviteModel.currentUserDisplay,
            "note" to inviteModel.note
        )

        database
            .collection("invite")
            .document(inviteModel.inviteId)
            .set(pairMap, SetOptions.merge())
            .await()

        return Response.Completed
    }

    override suspend fun inviteExists(inviteId: String): Boolean {
        val response = database.collection("invite")
            .document(inviteId)
            .get().await()

        return response.exists()
    }

    private companion object {
        private const val ACCEPTED_PAIR_INVITE_KEY = "ACCEPTED_PAIR_INVITE"
    }
}