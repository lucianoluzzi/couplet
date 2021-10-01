package com.couplesdating.couplet.data.repository

interface OnboardingRepository {
    fun setOnboardingShown()
    fun hasSeenOnboarding(): Boolean
}