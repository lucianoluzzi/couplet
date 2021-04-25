package com.couplesdating.couplet.ui.home

import com.couplesdating.couplet.domain.model.AcceptedInvite
import com.couplesdating.couplet.domain.model.User

data class HomeUIData(
    val user: User?,
    val acceptedInvite: AcceptedInvite?
)
