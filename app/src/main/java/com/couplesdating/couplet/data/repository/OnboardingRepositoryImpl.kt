package com.couplesdating.couplet.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.couplesdating.couplet.R

class OnboardingRepositoryImpl(
    private val context: Context,
    private val preferences: SharedPreferences
) : OnboardingRepository {

    override fun setOnboardingShown() {
        with(preferences.edit()) {
            putBoolean(context.getString(R.string.has_shown_onboarding_key), true)
            apply()
        }
    }

    override fun hasSeenOnboarding() = preferences.getBoolean(
        context.getString(R.string.has_shown_onboarding_key),
        false
    )
}