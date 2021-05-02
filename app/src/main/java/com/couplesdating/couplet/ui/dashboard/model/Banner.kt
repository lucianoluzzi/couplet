package com.couplesdating.couplet.ui.dashboard.model

import com.couplesdating.couplet.domain.model.InviteModel

sealed class Banner {
    data class PendingInvite(val invite: InviteModel) : Banner()
}
