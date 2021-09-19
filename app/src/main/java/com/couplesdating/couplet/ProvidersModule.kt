package com.couplesdating.couplet

import androidx.room.Room
import com.couplesdating.couplet.analytics.FirebaseAnalyticsProvider
import com.couplesdating.couplet.data.*
import com.couplesdating.couplet.data.database.CoupletDatabase
import com.couplesdating.couplet.notifications.AppLifecycleObserver
import com.couplesdating.couplet.notifications.NotificationServiceProvider
import com.couplesdating.couplet.ui.AppLifecycleObserverProvider
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

    single {
        Room.databaseBuilder(
            androidContext(),
            CoupletDatabase::class.java, "couplet-db"
        ).build()
    }

    single { AppLifecycleObserverProvider().appLifecycleObserver }
}