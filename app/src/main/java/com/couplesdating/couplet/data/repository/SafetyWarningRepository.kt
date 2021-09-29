package com.couplesdating.couplet.data.repository

interface SafetyWarningRepository {
    fun setHasSeenSafetyWarning()
    fun hasSeenSafetyWarning(): Boolean
}