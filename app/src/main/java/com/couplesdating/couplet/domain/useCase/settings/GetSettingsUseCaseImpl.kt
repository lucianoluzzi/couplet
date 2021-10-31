package com.couplesdating.couplet.domain.useCase.settings

import com.couplesdating.couplet.R
import com.couplesdating.couplet.ui.profile.model.SettingsItem

class GetSettingsUseCaseImpl : GetSettingsUseCase {

    override fun getSettings() = listOf(
        SettingsItem(
            icon = R.drawable.ic_terms,
            description = "Terms of usage"
        ),
        SettingsItem(
            icon = R.drawable.ic_star,
            description = "Rate this app"
        ),
        SettingsItem(
            icon = R.drawable.ic_delete,
            description = "Delete account"
        ),
        SettingsItem(
            icon = R.drawable.ic_exit,
            description = "Logout"
        )
    )
}