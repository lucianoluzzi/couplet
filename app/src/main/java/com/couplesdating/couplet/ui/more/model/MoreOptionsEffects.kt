package com.couplesdating.couplet.ui.more.model

import com.couplesdating.couplet.domain.model.User

sealed class MoreOptionsEffects {
    object NavigateToSeeAllMatches : MoreOptionsEffects()
    data class NavigateToMatch(val matchId: String) : MoreOptionsEffects()
    data class NavigateToPartner(val partner: User) : MoreOptionsEffects()
    data class NavigateToProfile(val user: User) : MoreOptionsEffects()
}
