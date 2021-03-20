package com.couplesdating.couplet.analytics.events.login

import com.couplesdating.couplet.analytics.events.AnalyticsEvent

private const val SCREEN_NAME = "social_login"

sealed class SocialLoginEvents {
    object LoginWithEmailCliked : AnalyticsEvent("${SCREEN_NAME}_login_with_email_clicked")
    object LoginWithGoogleClicked : AnalyticsEvent("${SCREEN_NAME}_login_with_google_clicked")
    object LoginWithFacebookClicked : AnalyticsEvent("${SCREEN_NAME}_login_with_facebook_clicked")
    object RegisterClicked : AnalyticsEvent("${SCREEN_NAME}_register_clicked")
}
