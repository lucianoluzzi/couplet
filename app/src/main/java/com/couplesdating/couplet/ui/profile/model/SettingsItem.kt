package com.couplesdating.couplet.ui.profile.model

import androidx.annotation.DrawableRes

data class SettingsItem(
    @DrawableRes val icon: Int,
    val description: String,
    val type: SettingsItemType
)


