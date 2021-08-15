package com.couplesdating.couplet.data.database.mapper

import com.couplesdating.couplet.data.database.entities.MatchEntity
import com.couplesdating.couplet.data.database.entities.MatchWithIdeaEntity
import com.couplesdating.couplet.domain.model.Match

class MatchMapper {
    fun matchToMatchWithIdeaEntity(match: Match) = MatchWithIdeaEntity(
        match = matchToMatchEntity(match),
        idea = MatchIdeaMapper().ideaToEntity(
            idea = match.idea
        )
    )

    fun matchToMatchEntity(match: Match) = MatchEntity(
        id = match.id,
        partnerResponse = match.partnerResponse,
        ideaId = match.idea.id
    )

    fun matchWithIdeaEntityListToMatchList(matches: List<MatchWithIdeaEntity>): List<Match> {
        return matches.map {
            matchWithIdeaEntityToMatch(it)
        }
    }

    private fun matchWithIdeaEntityToMatch(matchWithIdeaEntity: MatchWithIdeaEntity) = Match(
        id = matchWithIdeaEntity.match.id,
        idea = MatchIdeaMapper().ideaEntityToIdea(
            matchWithIdeaEntity.idea,
            matchWithIdeaEntity.idea.categoryId
        ),
        partnerResponse = matchWithIdeaEntity.match.partnerResponse
    )

    fun matchesToMatchWithIdeaList(matches: List<Match?>): List<MatchWithIdeaEntity?> {
        return matches.map {
            it?.let {
                matchToMatchWithIdeaEntity(it)
            }
        }
    }
}