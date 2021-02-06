package com.couplesdating.couplet

import android.app.Application
import com.facebook.appevents.AppEventsLogger
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppEventsLogger.activateApp(this)

        startKoin {
            androidContext(this@MainApplication)
            modules(
                appModule
            )
        }
    }
}