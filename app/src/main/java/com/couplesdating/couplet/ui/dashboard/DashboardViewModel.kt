package com.couplesdating.couplet.ui.dashboard

import androidx.lifecycle.*
import com.couplesdating.couplet.R
import com.couplesdating.couplet.analytics.Analytics
import com.couplesdating.couplet.analytics.events.dashboard.BannerEvents
import com.couplesdating.couplet.analytics.events.dashboard.CategoryEvents
import com.couplesdating.couplet.domain.extensions.isNull
import com.couplesdating.couplet.domain.model.Category
import com.couplesdating.couplet.domain.model.Match
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.domain.network.Response
import com.couplesdating.couplet.domain.useCase.category.GetCategoriesUseCase
import com.couplesdating.couplet.domain.useCase.category.RefreshCategoriesUseCase
import com.couplesdating.couplet.domain.useCase.invite.GetReceivedInviteUseCase
import com.couplesdating.couplet.domain.useCase.invite.GetSentPairInviteUseCase
import com.couplesdating.couplet.domain.useCase.match.GetNewMatchesUseCase
import com.couplesdating.couplet.domain.useCase.pair.GetPartnerUseCase
import com.couplesdating.couplet.domain.useCase.pair.SetSyncShownUseCase
import com.couplesdating.couplet.domain.useCase.pair.ShouldShowSyncUseCase
import com.couplesdating.couplet.domain.useCase.safetyWarning.GetHasSeenSafetyWarningUseCase
import com.couplesdating.couplet.ui.dashboard.adapter.CategoryUIModel
import com.couplesdating.couplet.ui.dashboard.model.Banner
import com.couplesdating.couplet.ui.dashboard.model.DashboardRoute
import com.couplesdating.couplet.ui.dashboard.model.DashboardUIState
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val shouldShowSyncUseCase: ShouldShowSyncUseCase,
    private val setSyncShownUseCase: SetSyncShownUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val refreshCategoriesUseCase: RefreshCategoriesUseCase,
    private val getReceivedInviteUseCase: GetReceivedInviteUseCase,
    private val getSentPairInviteUseCase: GetSentPairInviteUseCase,
    private val getNewMatchesUseCase: GetNewMatchesUseCase,
    private val getPartnerUseCase: GetPartnerUseCase,
    private val getHasSeenSafetyWarningUseCase: GetHasSeenSafetyWarningUseCase,
    private val analytics: Analytics,
    private val currentUser: User
) : ViewModel() {

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    private val categoriesLiveData = MutableLiveData<List<Category>>()
    private val bannerLiveData = MutableLiveData<Banner?>()
    val uiDataMediator = MediatorLiveData<DashboardUIState>()

    private val navigationChannel = Channel<DashboardRoute>(Channel.CONFLATED)
    val navigationFlow = navigationChannel.receiveAsFlow().distinctUntilChanged()

    init {
        uiDataMediator.addSource(categoriesLiveData) { categories ->
            val currentUiData = uiDataMediator.value
            if (currentUiData is DashboardUIState.Success) {
                uiDataMediator.value = DashboardUIState.Success(
                    categories = mapCategoryToUIModel(categories),
                    banner = currentUiData.banner
                )
            } else {
                val currentBanner = bannerLiveData.value
                uiDataMediator.value = DashboardUIState.Success(
                    categories = mapCategoryToUIModel(categories),
                    banner = currentBanner
                )
            }
        }
        uiDataMediator.addSource(bannerLiveData) { banner ->
            val currentUiData = uiDataMediator.value
            if (currentUiData is DashboardUIState.Success) {
                uiDataMediator.value = DashboardUIState.Success(
                    categories = currentUiData.categories,
                    banner = banner
                )
            } else {
                val currentCategories = categoriesLiveData.value
                currentCategories?.let {
                    uiDataMediator.value = DashboardUIState.Success(
                        categories = mapCategoryToUIModel(it),
                        banner = banner
                    )
                }
            }
        }

        viewModelScope.launch {
            shouldShowSyncUseCase.invoke(currentUser).collect {
                if (it) {
                    onSyncShown()
                    navigationChannel.send(DashboardRoute.ToSync)
                }
            }
        }
        viewModelScope.launch {
            getCategoriesUseCase.getCategories().collect {
                categoriesLiveData.value = it
            }
        }
        viewModelScope.launch {
            _loadingLiveData.value = true
            val refreshTask =
                async { refreshCategoriesUseCase.refreshCategories(currentUser.userId) }
            refreshTask.await()
            _loadingLiveData.value = false
        }
    }

    fun fetchBanner() {
        viewModelScope.launch {
            val banner = getBanner(currentUser)
            bannerLiveData.value = banner
        }
    }

    private suspend fun getBanner(currentUser: User): Banner? {
        val pendingInvite = getReceivedInviteUseCase.getReceivedInvite(currentUser.userId)
        val sentInvite = getSentPairInviteUseCase.getSentPairInvite(currentUser.userId)
        val newMatches = getNewMatchesUseCase.getNewMatches(currentUser.userId)
        val partnerResponse = getPartnerUseCase.getPartner(currentUser.userId)
        val partner = if (partnerResponse is Response.Success<*>) {
            (partnerResponse as Response.Success<User>).data
        } else {
            null
        }
        if (pendingInvite != null) {
            return Banner.PendingInvite(pendingInvite)
        }
        if (partner.isNull() && sentInvite.isNull().not()) {
            sentInvite?.let {
                return Banner.SentInvite(it)
            }
        }
        if (partner.isNull() && sentInvite.isNull()) {
            return Banner.RegisterPartner
        }
        if (newMatches is Response.Success<*>) {
            if (newMatches.data is List<*> && newMatches.data.isNotEmpty()) {
                return Banner.NewMatches(newMatches.data as List<Match>)
            }
        }
//        if (currentUser.isPremium.not()) {
//            return Banner.BecomePremium
//        }

        return null
    }

    private fun mapCategoryToUIModel(categories: List<Category>): List<CategoryUIModel> {
        return categories.mapIndexed { index, category ->
            CategoryUIModel(
                id = category.id,
                title = category.title,
                description = category.description,
                isPremium = category.isPremium,
                spiciness = category.spiciness,
                hasNewIdeas = category.hasNewIdeas,
                ideas = category.newIdeas,
                imageSmall = getCategorySmallImage(index),
                imageBig = getCategoryBigImage(index)
            )
        }
    }

    private fun getCategorySmallImage(position: Int): Int {
        return when (position) {
            0 -> R.drawable.ic_category_1
            1 -> R.drawable.ic_category_2
            2 -> R.drawable.ic_category_3
            3 -> R.drawable.ic_category_4
            else -> R.drawable.ic_category_1
        }
    }

    private fun getCategoryBigImage(position: Int): Int {
        return when (position) {
            0 -> R.drawable.ic_category_1_big
            1 -> R.drawable.ic_category_2_big
            2 -> R.drawable.ic_category_3_big
            3 -> R.drawable.ic_category_4_big
            else -> R.drawable.ic_category_1_big
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
            val hasNotSeenSafetyWarning =
                getHasSeenSafetyWarningUseCase.hasSeenSafetyWarning().not()
            val route = if (category.spiciness >= 4 && hasNotSeenSafetyWarning) {
                DashboardRoute.ToSafetyWarning(
                    category = category,
                    ideas = category.ideas
                )
            } else {
                DashboardRoute.ToIdeas(
                    category = category,
                    ideas = category.ideas
                )
            }
            navigationChannel.send(route)
        }
    }
}