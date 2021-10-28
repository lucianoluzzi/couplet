package com.couplesdating.couplet.ui.more.model

import com.couplesdating.couplet.ui.more.RecentMatch

sealed class MoreOptionsState {
    data class WithMatchesState(val matches: List<RecentMatch>) : MoreOptionsState()
    object WithoutMatchesState : MoreOptionsState()
}
