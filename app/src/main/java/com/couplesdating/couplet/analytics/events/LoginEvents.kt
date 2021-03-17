package com.couplesdating.couplet.analytics.events

private const val SCREEN_NAME = "login_"

object LoginClicked : AnalyticsEvent("${SCREEN_NAME}_login_clicked")
object ForgotPasswordClicked : AnalyticsEvent("${SCREEN_NAME}_forgot_password_clicked")
object EmailClicked : AnalyticsEvent("${SCREEN_NAME}_email_clicked")
object PasswordClicked : AnalyticsEvent("${SCREEN_NAME}_password_clicked")
object EmailEmptyState : AnalyticsEvent("${SCREEN_NAME}_email_empy")
object PasswordEmptyState : AnalyticsEvent("${SCREEN_NAME}_password_empy")
object AuthenticationErrorState : AnalyticsEvent("${SCREEN_NAME}_authentication_error")
object SuccessState : AnalyticsEvent("${SCREEN_NAME}_login_success")