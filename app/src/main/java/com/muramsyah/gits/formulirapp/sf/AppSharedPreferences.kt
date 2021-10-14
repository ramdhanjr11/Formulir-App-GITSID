package com.muramsyah.gits.formulirapp.sf

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSharedPreferences @Inject constructor(context: Context) {

    companion object {
        const val PREFS_NAME = "app_pref"
        const val SESSION_LOGIN_KEY = "login_pref"
        const val USER_ID_KEY = "user_id"
    }

    val sharedPrefApp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var loginSession: Boolean
        get() = sharedPrefApp.getBoolean(SESSION_LOGIN_KEY, false)
        set(value) {
            sharedPrefApp.edit()
                .putBoolean(SESSION_LOGIN_KEY, value)
                .apply()
        }

    var userId: String
        get() = sharedPrefApp.getString(USER_ID_KEY, "")!!
        set(value) {
            sharedPrefApp.edit()
                .putString(USER_ID_KEY, value)
                .apply()
        }

}