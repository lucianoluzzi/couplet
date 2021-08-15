package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.data.dataSource.match.MatchLocalDataSource
import com.couplesdating.couplet.data.dataSource.match.MatchRemoteDataSource
import com.couplesdating.couplet.data.database.mapper.MatchMapper
import com.couplesdating.couplet.domain.model.Match
import com.couplesdating.couplet.domain.network.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MatchRepositoryImpl(
    private val localDataSource: MatchLocalDataSource,
    private val remoteDataSource: MatchRemoteDataSource
) : MatchRepository {

    override suspend fun getNewMatches(currentUserId: String): Flow<List<Match>> {
        return localDataSource.getMatches().map { matchWithIdeaEntity ->
            MatchMapper().matchWithIdeaEntityListToMatchList(matchWithIdeaEntity)
        }
    }

    override suspend fun deleteAllMatches(currentUserId: String): Response {
        return try {
            val response = remoteDataSource.deleteAllMatches(currentUserId)
            if (response is Response.Success<*> || response is Response.Completed) {
                localDataSource.deleteAllMatches()
            }
            Response.Completed
        } catch (exception: Exception) {
            Response.Error(exception.toString())
        }
    }

    override suspend fun deleteMatch(match: Match): Response {
        return try {
            val response = remoteDataSource.deleteMatch(match.id)
            if (response is Response.Success<*> || response is Response.Completed) {
                localDataSource.deleteMatch(
                    matchEntity = MatchMapper().matchToMatchEntity(
                        match
                    )
                )
            }
            Response.Completed
        } catch (exception: Exception) {
            Response.Error(exception.toString())
        }
    }

    override suspend fun refreshMatches(currentUserId: String) {
        val response = remoteDataSource.getMatches(currentUserId)
        if (response is Response.Success<*>) {
            localDataSource.deleteAllMatches()
            val matchesResponse = response.data as List<Match?>
            val entities = MatchMapper().matchesToMatchWithIdeaList(
                matchesResponse
            ).mapNotNull {
                it
            }
            localDataSource.insertAll(entities)
        }
    }
}