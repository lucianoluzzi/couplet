package com.couplesdating.couplet.data.dataSource.match

import com.couplesdating.couplet.data.database.dao.MatchDao
import com.couplesdating.couplet.data.database.dao.MatchIdeaDao
import com.couplesdating.couplet.data.database.dao.MatchWithIdeaDao
import com.couplesdating.couplet.data.database.entities.MatchEntity
import com.couplesdating.couplet.data.database.entities.MatchWithIdeaEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MatchLocalDataSource(
    private val matchDao: MatchDao,
    private val ideaDao: MatchIdeaDao,
    private val matchWithIdeaDao: MatchWithIdeaDao
) {

    suspend fun insertAll(matches: List<MatchWithIdeaEntity>) = withContext(Dispatchers.IO) {
        matches.map { matchWithIdeaEntity ->
            ideaDao.insert(matchWithIdeaEntity.idea)
            matchDao.insert(matchWithIdeaEntity.match)
        }
    }

    suspend fun getMatches(): Flow<List<MatchWithIdeaEntity>> = withContext(Dispatchers.IO) {
        return@withContext matchWithIdeaDao.getMatchesWithIdeas()
    }

    suspend fun deleteMatch(matchEntity: MatchEntity) = withContext(Dispatchers.IO) {
        matchDao.remove(matchEntity)
    }

    suspend fun deleteAllMatches() = withContext(Dispatchers.IO) {
        matchDao.removeAll()
    }
}