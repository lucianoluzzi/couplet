package com.couplesdating.couplet.domain.useCase.settings

import com.couplesdating.couplet.R
import com.couplesdating.couplet.ui.profile.model.SettingsItem
import com.couplesdating.couplet.ui.profile.model.SettingsItemType

class GetSettingsUseCaseImpl : GetSettingsUseCase {

    override fun getSettings() = listOf(
        SettingsItem(
            icon = R.drawable.ic_terms,
            description = "Terms of usage",
            type = SettingsItemType.TERMS
        ),
        SettingsItem(
            icon = R.drawable.ic_star,
            description = "Rate this app",
            type = SettingsItemType.RATE
        ),
        SettingsItem(
            icon = R.drawable.ic_delete,
            description = "Delete account",
            type = SettingsItemType.DELETE
        ),
        SettingsItem(
            icon = R.drawable.ic_exit,
            description = "Logout",
            type = SettingsItemType.LOGOFF
        )
    )
}