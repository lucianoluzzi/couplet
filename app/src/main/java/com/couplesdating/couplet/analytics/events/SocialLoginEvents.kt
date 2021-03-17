package com.couplesdating.couplet.analytics.events

private const val SCREEN_NAME = "social_login_"

sealed class SocialLoginEvents {
    object LoginWithEmailCliked : AnalyticsEvent("${SCREEN_NAME}login_with_email_clicked")
    object LoginWithGoogleClicked : AnalyticsEvent("${SCREEN_NAME}login_with_google_clicked")
    object LoginWithFacebookClicked : AnalyticsEvent("${SCREEN_NAME}login_with_facebook_clicked")
    object RegisterClicked : AnalyticsEvent("${SCREEN_NAME}register_clicked")
}
