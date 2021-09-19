package com.couplesdating.couplet.notifications

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent

import androidx.lifecycle.LifecycleObserver

class AppLifecycleObserver : LifecycleObserver {
    var isOpen = false
        private set

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEnterForeground() {
        isOpen = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground() {
        isOpen = false
    }

    companion object {
        val TAG = AppLifecycleObserver::class.java.name
    }
}