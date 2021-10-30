package com.couplesdating.couplet.ui.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.moreOptions.MoreOptionsEvents
import com.couplesdating.couplet.domain.model.Match
import com.couplesdating.couplet.domain.useCase.match.GetRecentMatchesUseCase
import com.couplesdating.couplet.ui.more.model.MoreOptionsEffects
import com.couplesdating.couplet.ui.more.model.MoreOptionsIntents
import com.couplesdating.couplet.ui.more.model.MoreOptionsState
import com.couplesdating.couplet.ui.more.model.RecentMatch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MoreOptionsViewModel(
    private val getRecentMatchesUseCase: GetRecentMatchesUseCase,
    private val analytics: Analytics
) : ViewModel() {
    private val _ideasLiveData = MutableLiveData<MoreOptionsState>()
    val ideasLiveData: LiveData<MoreOptionsState> = _ideasLiveData
    private val _effectsLiveData = Channel<MoreOptionsEffects>(Channel.CONFLATED)
    val effectsLiveData = _effectsLiveData.receiveAsFlow().distinctUntilChanged()

    fun getRecentMatches(matches: List<Match>) {
        viewModelScope.launch {
            val recentMatches = getRecentMatchesUseCase.getRecentMatches(matches)
            val recentMatchesNumbered = recentMatches.mapIndexed { index, match ->
                RecentMatch(
                    id = match.id,
                    index = index,
                    description = match.idea.description
                )
            }
            _ideasLiveData.value = if (recentMatchesNumbered.isNotEmpty()) {
                MoreOptionsState.WithMatchesState(recentMatchesNumbered)
            } else {
                MoreOptionsState.WithoutMatchesState
            }
        }
    }

    fun onIntent(intent: MoreOptionsIntents) {
        viewModelScope.launch {
            when (intent) {
                MoreOptionsIntents.SeeAllMatchesClick -> {
                    analytics.trackEvent(MoreOptionsEvents.SeeAllMatchesClicked)
                    _effectsLiveData.send(MoreOptionsEffects.NavigateToSeeAllMatches)
                }
                MoreOptionsIntents.PartnerClick -> {
                    analytics.trackEvent(MoreOptionsEvents.PartnerClicked)
                    _effectsLiveData.send(MoreOptionsEffects.NavigateToPartner)
                }
                MoreOptionsIntents.ProfileClick -> {
                    analytics.trackEvent(MoreOptionsEvents.ProfileClicked)
                    _effectsLiveData.send(MoreOptionsEffects.NavigateToProfile)
                }
                MoreOptionsIntents.ShareClick -> {
                    analytics.trackEvent(MoreOptionsEvents.ShareClicked)
                    _effectsLiveData.send(
                        MoreOptionsEffects.Share(SHARE_LINK)
                    )
                }
                is MoreOptionsIntents.MatchClick -> {
                    analytics.trackEvent(MoreOptionsEvents.MatchClicked)
                    _effectsLiveData.send(
                        MoreOptionsEffects.NavigateToMatch(intent.matchId)
                    )
                }
            }
        }
    }

    private companion object {
        private const val SHARE_LINK =
            "https://play.google.com/store/apps/details?id=com.couplesdating.couplet"
    }
}