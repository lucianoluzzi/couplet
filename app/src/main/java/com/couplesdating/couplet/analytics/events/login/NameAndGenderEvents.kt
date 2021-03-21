package com.couplesdating.couplet.analytics.events.login

import com.couplesdating.couplet.analytics.events.AnalyticsEvent

private const val SCREEN_NAME = "name_and_gender"

sealed class NameAndGenderEvents {
    object NameEmpty : AnalyticsEvent("${SCREEN_NAME}_name_empty")
    object GenderEmpty : AnalyticsEvent("${SCREEN_NAME}_gender_empty")
    object OtherGenderEmpty : AnalyticsEvent("${SCREEN_NAME}_other_gender_empty")
    object RegisterClick : AnalyticsEvent("${SCREEN_NAME}_register_click")
    object UpdateSuccess : AnalyticsEvent("${SCREEN_NAME}_update_success")
    data class UpdateError(val error: String?) : AnalyticsEvent(
        "${SCREEN_NAME}_update_error",
        mapOf(
            "error" to error
        )
    )
    object TermsOfUsageClick : AnalyticsEvent("${SCREEN_NAME}_terms_of_usage_click")
    object NameInputClick : AnalyticsEvent("${SCREEN_NAME}_name_click")
    object GenderInputClick : AnalyticsEvent("${SCREEN_NAME}_gender_click")
    object OtherGenderInputClick : AnalyticsEvent("${SCREEN_NAME}_other_gender_click")
}