package com.couplesdating.couplet.data

import com.couplesdating.couplet.analytics.FirebaseAnalyticsProvider
import com.couplesdating.couplet.notifications.NotificationServiceProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val providersModule = module {
    single { SharedPreferencesProvider(androidContext()).preferences }

    single { DynamicLinkProvider() }

    single { FirestoreProvider().firestoreDatabase }

    single { FirebaseAnalyticsProvider().analytics }

    single { FirebaseAuthProvider().firebaseAuth }

    single { ServiceProvider().functions }

    single { NotificationServiceProvider().messaging }
}