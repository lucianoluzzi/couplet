package com.couplesdating.couplet.data.repository

import android.content.SharedPreferences
import com.couplesdating.couplet.domain.model.InviteModel
import com.couplesdating.couplet.domain.model.Response
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await
import java.util.*

class InviteRepositoryImpl(
    private val database: FirebaseFirestore,
    private val preferences: SharedPreferences
) : InviteRepository {

    override fun savePairInvite(invite: InviteModel) {
        val inviteJson = Gson().toJson(invite)
        with(preferences.edit()) {
            putString(RECEIVED_PAIR_INVITE_KEY, inviteJson)
            apply()
        }
    }

    override suspend fun getSentPairInvite(currentUserId: String): InviteModel? {
        val response = database.collection("invite")
            .whereEqualTo("inviter_id", currentUserId)
            .get()
            .await()
        return response.documents.map { document ->
            InviteModel(
                inviterId = document.get("inviter_id").toString(),
                inviteeId = document.get("invitee_id").toString(),
                inviterDisplayName = document.get("inviter_display_name").toString(),
                inputInviteeDisplayName = document.get("invitee_input_display_name").toString(),
                note = document.get("note").toString(),
                inviteId = document.get("invite_id").toString(),
                timestamp = (document.get("timestamp") as Timestamp).toDate(),
                hasAccepted = false
            )
        }.firstOrNull()
    }

    override suspend fun getReceivedPairInvite(currentUserId: String?): InviteModel? {
        val inviteJson = preferences.getString(RECEIVED_PAIR_INVITE_KEY, null)
        return inviteJson?.let {
            Gson().fromJson(it, InviteModel::class.java)
        } ?: run {
            return currentUserId?.let {
                val response = database.collection("invite")
                    .whereEqualTo("invitee_id", currentUserId)
                    .get()
                    .await()
                response.documents.map { document ->
                    InviteModel(
                        inviterId = document.get("inviter_id").toString(),
                        inviteeId = document.get("invitee_id").toString(),
                        inviterDisplayName = document.get("inviter_display_name").toString(),
                        inputInviteeDisplayName = document.get("invitee_input_display_name").toString(),
                        note = document.get("note").toString(),
                        inviteId = document.get("invite_id").toString(),
                        timestamp = (document.get("timestamp") as Timestamp).toDate(),
                        hasAccepted = false
                    )
                }.firstOrNull()
            }
        }
    }

    override suspend fun deletePairInvite(inviteId: String) {
        database.collection("invite")
            .document(inviteId)
            .delete()
            .await()

        with(preferences.edit()) {
            putString(RECEIVED_PAIR_INVITE_KEY, null)
            apply()
        }
    }

    override suspend fun saveInvite(inviteModel: InviteModel): Response {
        val pairMap = hashMapOf<String, Any>(
            "inviter_id" to inviteModel.inviterId,
            "invite_id" to inviteModel.inviteId,
            "inviter_display_name" to inviteModel.inviterDisplayName,
        )
        inviteModel.note?.let {
            pairMap.put("note", inviteModel.note)
        }
        inviteModel.inputInviteeDisplayName?.let {
            pairMap.put("invitee_input_display_name", inviteModel.inputInviteeDisplayName)
        }
        inviteModel.timestamp?.let {
            pairMap.put("timestamp", Timestamp(it))
        }
        inviteModel.inviteeId?.let {
            pairMap.put("invitee_id", it)
        }

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
        private const val RECEIVED_PAIR_INVITE_KEY = "RECEIVED_PAIR_INVITE"
    }
}