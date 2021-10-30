package com.couplesdating.couplet.ui.more.model

sealed class MoreOptionsEffects {
    data class NavigateToMatch(val matchId: String) : MoreOptionsEffects()
    data class Share(val shareLink: String) : MoreOptionsEffects()
    object NavigateToSeeAllMatches : MoreOptionsEffects()
    object NavigateToPartner : MoreOptionsEffects()
    object NavigateToProfile : MoreOptionsEffects()
}
