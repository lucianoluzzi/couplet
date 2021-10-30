package com.couplesdating.couplet.ui.more.model

sealed class MoreOptionsIntents {
    object SeeAllMatchesClick : MoreOptionsIntents()
    object PartnerClick : MoreOptionsIntents()
    object ProfileClick : MoreOptionsIntents()
    object ShareClick : MoreOptionsIntents()
}
