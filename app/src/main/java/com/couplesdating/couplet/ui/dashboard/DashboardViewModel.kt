package com.couplesdating.couplet.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.R
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.dashboard.BannerEvents
import com.couplesdating.couplet.analytics.events.dashboard.CategoryEvents
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
import com.couplesdating.couplet.ui.dashboard.model.DashboardRoute
import com.couplesdating.couplet.ui.dashboard.model.DashboardUIState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val shouldShowSyncUseCase: ShouldShowSyncUseCase,
    private val setSyncShownUseCase: SetSyncShownUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getReceivedInviteUseCase: GetReceivedInviteUseCase,
    private val getSentPairInviteUseCase: GetSentPairInviteUseCase,
    private val getNewMatchesUseCase: GetNewMatchesUseCase,
    private val analytics: Analytics,
    private val currentUser: User
) : ViewModel() {

    private val _uiData = MutableLiveData<DashboardUIState>()
    val uiData: LiveData<DashboardUIState> = _uiData

    private val navigationChannel = Channel<DashboardRoute>(Channel.CONFLATED)
    val navigationFlow = navigationChannel.receiveAsFlow().distinctUntilChanged()

    init {
        viewModelScope.launch {
            shouldShowSyncUseCase.invoke(currentUser).collect {
                if (it) {
                    onSyncShown()
                    navigationChannel.send(DashboardRoute.ToSync)
                }
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
                id = category.id,
                title = category.title,
                description = category.description,
                isPremium = category.isPremium,
                spiciness = category.spiciness,
                hasNewIdeas = category.hasNewIdeas,
                ideas = category.newIdeas,
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

    private fun onSyncShown() {
        setSyncShownUseCase.invoke()
    }

    fun onBannerClicked(banner: Banner) {
        when (banner) {
            Banner.BecomePremium -> analytics.trackEvent(BannerEvents.BecomePremiumClicked)
            is Banner.NewMatches -> analytics.trackEvent(BannerEvents.NewMatchesClicked)
            is Banner.PendingInvite -> analytics.trackEvent(BannerEvents.PendingInviteClicked)
            Banner.RegisterPartner -> analytics.trackEvent(BannerEvents.RegisterPartnerClicked)
        }
    }

    fun onCategoryClicked(category: CategoryUIModel) {
        analytics.trackEvent(CategoryEvents.OnCategoryClicked(categoryId = category.id))
        viewModelScope.launch {
            navigationChannel.send(
                DashboardRoute.ToIdeas(
                    categoryName = category.title,
                    ideas = category.ideas
                )
            )
        }
    }
}