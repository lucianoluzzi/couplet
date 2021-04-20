package com.couplesdating.couplet

import android.app.Application
import com.facebook.appevents.AppEventsLogger
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.KoinExperimentalAPI
import org.koin.core.context.startKoin

@KoinExperimentalAPI
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppEventsLogger.activateApp(this)

        startKoin {
            androidContext(this@MainApplication)
            fragmentFactory()
            modules(appModule)
        }
    }
}