package com.couplesdating.couplet.notifications

import org.koin.dsl.module

val notificationModule = module {
    single {
        NotificationChannelManager(get())
    }
}