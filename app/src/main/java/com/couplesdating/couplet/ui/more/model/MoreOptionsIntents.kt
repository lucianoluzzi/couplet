package com.couplesdating.couplet.ui.more.model

sealed class MoreOptionsIntents {
    data class MatchClick(val matchId: String) : MoreOptionsIntents()
    object SeeAllMatchesClick : MoreOptionsIntents()
    object PartnerClick : MoreOptionsIntents()
    object ProfileClick : MoreOptionsIntents()
    object ShareClick : MoreOptionsIntents()
}
