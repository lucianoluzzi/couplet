package com.couplesdating.couplet.data.repository

import android.content.SharedPreferences

class SafetyWarningRepositoryImpl(
    private val preferences: SharedPreferences
) : SafetyWarningRepository {
    override fun setHasSeenSafetyWarning() {
        with(preferences.edit()) {
            putBoolean(HAS_SEEN_SAFETY_WARNING_KEY, true)
            apply()
        }
    }

    override fun hasSeenSafetyWarning() = preferences.getBoolean(
        HAS_SEEN_SAFETY_WARNING_KEY,
        false
    )

    private companion object {
        private const val HAS_SEEN_SAFETY_WARNING_KEY = "HAS_SEEN_SAFETY_WARNING"
    }
}