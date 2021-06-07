package com.couplesdating.couplet.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.R
import com.couplesdating.couplet.domain.extensions.isNull
import com.couplesdating.couplet.domain.model.Category
import com.couplesdating.couplet.domain.model.Match
import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.domain.useCase.category.GetCategoriesUseCase
import com.couplesdating.couplet.domain.useCase.invite.GetReceivedInviteUseCase
import com.couplesdating.couplet.domain.useCase.invite.GetSentPairInviteUseCase
import com.couplesdating.couplet.domain.useCase.match.GetNewMatchesUseCase
import com.couplesdating.couplet.domain.useCase.pair.SetSyncShownUseCase
import com.couplesdating.couplet.domain.useCase.pair.ShouldShowSyncUseCase
import com.couplesdating.couplet.ui.dashboard.adapter.CategoryUIModel
import com.couplesdating.couplet.ui.dashboard.model.Banner
import com.couplesdating.couplet.ui.dashboard.model.DashboardUIState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val shouldShowSyncUseCase: ShouldShowSyncUseCase,
    private val setSyncShownUseCase: SetSyncShownUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getReceivedInviteUseCase: GetReceivedInviteUseCase,
    private val getSentPairInviteUseCase: GetSentPairInviteUseCase,
    private val getNewMatchesUseCase: GetNewMatchesUseCase
) : ViewModel() {

    private val _shouldShowSync = MutableLiveData<Boolean>()
    val shouldShowSync: LiveData<Boolean> = _shouldShowSync

    private val _uiData = MutableLiveData<DashboardUIState>()
    val uiData: LiveData<DashboardUIState> = _uiData

    fun init(currentUser: User) {
        viewModelScope.launch {
            shouldShowSyncUseCase.invoke(currentUser).collect {
                _shouldShowSync.value = it
            }
        }

        viewModelScope.launch {
            _uiData.value = DashboardUIState.Loading
            getCategoriesUseCase.getCategories(currentUser.userId).collect {
                _uiData.value = DashboardUIState.Success(
                    categories = mapCategoryToUIModel(it),
                    banner = getBanner(currentUser)
                )
            }
        }
    }

    private suspend fun getBanner(currentUser: User): Banner? {
        val pendingInvite = getReceivedInviteUseCase.getReceivedInvite(currentUser.userId)
        val receivedInvite = getSentPairInviteUseCase.getSentPairInvite(currentUser.userId)
        val newMatches = getNewMatchesUseCase.getNewMatches(currentUser.userId)
        if (pendingInvite != null) {
            return Banner.PendingInvite(pendingInvite)
        }
        if (currentUser.pairedPartner.isNull() && receivedInvite.isNull()) {
            return Banner.RegisterPartner
        }
        if (newMatches is Response.Success<*>) {
            if (newMatches.data is List<*> && newMatches.data.isNotEmpty()) {
                return Banner.NewMatches(newMatches.data as List<Match>)
            }
        }
        if (currentUser.isPremium.not()) {
            return Banner.BecomePremium
        }

        return null
    }

    private fun mapCategoryToUIModel(categories: List<Category>): List<CategoryUIModel> {
        return categories.map { category ->
            CategoryUIModel(
                title = category.title,
                description = category.description,
                isPremium = category.isPremium,
                spiciness = category.spiciness,
                hasNewIdeas = category.hasNewIdeas,
                image = getCategoryImage(category.id)
            )
        }
    }

    private fun getCategoryImage(categoryId: String): Int {
        return when (categoryId) {
            "1" -> R.drawable.ic_category_1
            "2" -> R.drawable.ic_category_2
            "3" -> R.drawable.ic_category_3
            "4" -> R.drawable.ic_category_4
            else -> R.drawable.ic_category_1
        }
    }

    fun onSyncShown() {
        setSyncShownUseCase.invoke()
    }
}