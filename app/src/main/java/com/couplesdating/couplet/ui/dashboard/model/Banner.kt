package com.couplesdating.couplet.ui.dashboard.model

import com.couplesdating.couplet.domain.model.InviteModel
import com.couplesdating.couplet.domain.model.Match

sealed class Banner {
    data class PendingInvite(val invite: InviteModel) : Banner()
    data class SentInvite(val invite: InviteModel) : Banner()
    object RegisterPartner : Banner()
    data class NewMatches(val newMatches: List<Match>) : Banner()
    object BecomePremium : Banner()
}
