package com.couplesdating.couplet.ui.home

import com.couplesdating.couplet.domain.model.InviteModel
import com.couplesdating.couplet.domain.model.User

data class HomeUIData(
    val user: User?,
    val invite: InviteModel?
)
