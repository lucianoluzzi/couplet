package com.couplesdating.couplet.analytics.events

private const val SCREEN_NAME = "forgot_password_"

sealed class ForgotPasswordEvents {
    object SendLinkClicked : AnalyticsEvent("${SCREEN_NAME}_send_link_clicked")
    object EmailInputClicked : AnalyticsEvent("${SCREEN_NAME}_email_clicked")
    object EmailEmptyState : AnalyticsEvent("${SCREEN_NAME}_email_empty")
    object ResetLinkSent : AnalyticsEvent("${SCREEN_NAME}_reset_link_sent")
    data class SendLinkError(val error: String?) : AnalyticsEvent("${SCREEN_NAME}_send_link_error")
    object SnackBarDismissed : AnalyticsEvent("${SCREEN_NAME}_snackbar_dismissed")
}
