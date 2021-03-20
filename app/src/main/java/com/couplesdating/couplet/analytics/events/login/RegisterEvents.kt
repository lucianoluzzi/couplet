package com.couplesdating.couplet.analytics.events.login

import com.couplesdating.couplet.analytics.events.AnalyticsEvent

private const val SCREEN_NAME = "register"

sealed class RegisterEvents {
    object RegisterClicked : AnalyticsEvent("${SCREEN_NAME}_register_clicked")
    object EmailEmpty : AnalyticsEvent("${SCREEN_NAME}_email_empty")
    object PasswordEmpty : AnalyticsEvent("${SCREEN_NAME}_password_empty")
    object ConfirmPasswordEmpty : AnalyticsEvent("${SCREEN_NAME}_confirm_password_empty")
    object PasswordsDontMatch : AnalyticsEvent("${SCREEN_NAME}_passwords_dont_match")
    data class RegisterError(val error: String?) : AnalyticsEvent(
        "${SCREEN_NAME}_register_error",
        mapOf(
            "error" to error
        )
    )

    object RegisterSuccess : AnalyticsEvent("${SCREEN_NAME}_register_success")
}