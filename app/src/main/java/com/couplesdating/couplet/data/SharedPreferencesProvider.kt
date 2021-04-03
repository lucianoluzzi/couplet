package com.couplesdating.couplet.data

import android.content.Context

class SharedPreferencesProvider(
    context: Context
) {
    val preferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)

    private companion object {
        private const val SHARED_PREFERENCES_FILE = "com.couplesdating.couplet.data.PREFS"
    }
}