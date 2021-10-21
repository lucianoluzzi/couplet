package com.couplesdating.couplet.domain.useCase.match

import com.couplesdating.couplet.domain.model.Match

class GetRecentMatchesUseCaseImpl : GetRecentMatchesUseCase {

    override fun getRecentMatches(matches: List<Match>): List<Match> {
        if (matches.isEmpty()) return emptyList()
        val recentMatches = mutableListOf<Match>()
        for (i in matches.lastIndex downTo matches.lastIndex - 3) {
            if (i > 0) {
                val match = matches[i]
                recentMatches.add(match)
            }
        }
        return recentMatches
    }
}