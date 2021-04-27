package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.model.Category
import com.couplesdating.couplet.domain.model.Response

class CategoryRepositoryImpl : CategoryRepository {

    override suspend fun getCategories(): Response {
        val categories = listOf(
            Category(
                id = "1",
                title = "Esquenta Relação",
                description = "Vamos esquentar essa relação?",
                isPremium = false,
                spiciness = 1,
                hasNewIdeas = false
            ),
            Category(
                id = "2",
                title = "Hoje é o dia!",
                description = "Que tal apimentar as coisas?",
                isPremium = true,
                spiciness = 2,
                hasNewIdeas = false
            ),
            Category(
                id = "3",
                title = "Agora vai",
                description = "É agora ou nunca!",
                isPremium = true,
                spiciness = 3,
                hasNewIdeas = true
            ),
            Category(
                id = "4",
                title = "Só se vive uma vez!",
                description = "Essa é a hora certa, está pronta?",
                isPremium = false,
                spiciness = 1,
                hasNewIdeas = false
            )
        )
        return Response.Success(categories)
    }
}