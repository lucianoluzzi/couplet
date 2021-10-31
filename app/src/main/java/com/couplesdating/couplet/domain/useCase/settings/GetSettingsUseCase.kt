package com.couplesdating.couplet.domain.useCase.settings

import com.couplesdating.couplet.ui.profile.model.SettingsItem

interface GetSettingsUseCase {
    fun getSettings(): List<SettingsItem>
}