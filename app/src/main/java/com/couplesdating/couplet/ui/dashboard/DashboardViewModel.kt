package com.couplesdating.couplet.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.couplesdating.couplet.R
import com.couplesdating.couplet.domain.model.Category
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.domain.useCase.category.GetCategoriesUseCase
import com.couplesdating.couplet.domain.useCase.invite.GetInviteUseCase
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
    private val getInviteUseCase: GetInviteUseCase
) : ViewModel() {

    private val _shouldShowSync = MutableLiveData<Boolean>()
    val shouldShowSync: LiveData<Boolean> = _shouldShowSync

    private val _uiData = MutableLiveData<DashboardUIState>()
    val uiData: LiveData<DashboardUIState> = _uiData

    fun init(currentUser: User?) {
        viewModelScope.launch {
            shouldShowSyncUseCase.invoke().collect {
                _shouldShowSync.value = it && currentUser?.pairedPartner == null
            }
        }

        viewModelScope.launch {
            val pendingInvite = getInviteUseCase.getInvite(currentUser?.userId)
            getCategoriesUseCase.getCategories().collect {
                _uiData.value = DashboardUIState.Success(
                    categories = mapCategoryToUIModel(it),
                    banner = if (pendingInvite != null) Banner.PendingInvite(pendingInvite) else null
                )
            }
        }
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