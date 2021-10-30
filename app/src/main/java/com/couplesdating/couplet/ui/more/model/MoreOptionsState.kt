package com.couplesdating.couplet.ui.more.model

sealed class MoreOptionsState {
    data class WithMatchesState(val matches: List<RecentMatch>) : MoreOptionsState()
    object WithoutMatchesState : MoreOptionsState()
}
