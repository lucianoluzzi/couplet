package com.couplesdating.couplet

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.couplesdating.couplet.data.dataSource.dataSourceModule
import com.couplesdating.couplet.data.database.dao.daoModule
import com.couplesdating.couplet.data.repository.repositoryModule
import com.couplesdating.couplet.domain.useCaseModule
import com.couplesdating.couplet.notifications.AppLifecycleObserver
import com.couplesdating.couplet.notifications.NotificationChannelManager
import com.couplesdating.couplet.notifications.notificationModule
import com.couplesdating.couplet.ui.fragmentModule
import com.facebook.appevents.AppEventsLogger
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.KoinExperimentalAPI
import org.koin.core.context.startKoin

@KoinExperimentalAPI
class MainApplication : Application() {
    private val applicationLifecycleObserver by inject<AppLifecycleObserver>()
    private val notificationChannelManager by inject<NotificationChannelManager>()

    override fun onCreate() {
        super.onCreate()
        AppEventsLogger.activateApp(this)

        startKoin {
            androidContext(this@MainApplication)
            fragmentFactory()
            modules(
                repositoryModule,
                providersModule,
                useCaseModule,
                fragmentModule,
                dataSourceModule,
                daoModule,
                appModule,
                notificationModule
            )
        }
        notificationChannelManager.createNotificationChannel()
        ProcessLifecycleOwner.get().lifecycle.addObserver(applicationLifecycleObserver)
    }
}