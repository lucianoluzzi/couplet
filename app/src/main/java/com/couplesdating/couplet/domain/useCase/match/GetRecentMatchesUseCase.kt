package com.couplesdating.couplet.domain.useCase.match

import com.couplesdating.couplet.domain.model.Match

interface GetRecentMatchesUseCase {
    fun getRecentMatches(matches: List<Match>): List<Match>
}