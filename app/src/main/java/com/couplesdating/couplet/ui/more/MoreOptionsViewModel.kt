package com.couplesdating.couplet.ui.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.domain.model.Match
import com.couplesdating.couplet.domain.useCase.match.GetRecentMatchesUseCase
import kotlinx.coroutines.launch

class MoreOptionsViewModel(
    private val getRecentMatchesUseCase: GetRecentMatchesUseCase
) : ViewModel() {
    private val _ideasLiveData = MutableLiveData<List<RecentMatch>>()
    val ideasLiveData: LiveData<List<RecentMatch>> = _ideasLiveData

    fun getRecentMatches(matches: List<Match>) {
        viewModelScope.launch {
            val recentMatches = getRecentMatchesUseCase.getRecentMatches(matches)
            val recentMatchesNumbered = recentMatches.mapIndexed { index, match ->
                RecentMatch(
                    number = index,
                    description = match.idea.description
                )
            }
            _ideasLiveData.value = recentMatchesNumbered
        }
    }
}