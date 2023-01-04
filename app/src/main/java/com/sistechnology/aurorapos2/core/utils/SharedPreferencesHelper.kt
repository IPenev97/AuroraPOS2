package com.sistechnology.aurorapos2.core.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreferencesHelper {
    companion object {

        private var prefs: SharedPreferences? = null
        private var IS_FIRST_START = "isFirstStart"
        private var CURRENT_USER = "currentUser"
        private var CURRENT_USER_ID = "currentUserId"

        @Volatile
        private var instance: SharedPreferencesHelper? = null

        fun getInstance(context: Context): SharedPreferencesHelper {
            synchronized(this) {
                val _instance = instance
                if (_instance == null) {
                    prefs = PreferenceManager.getDefaultSharedPreferences(context)
                    instance = _instance
                }
                return SharedPreferencesHelper()
            }
        }
    }

    fun getIsFirstRun() = prefs?.getBoolean(IS_FIRST_START, true)

    fun setIsFirstRun(isFirstRun: Boolean) {
        prefs?.edit(commit = true) { putBoolean(IS_FIRST_START, isFirstRun) }
    }

    fun getCurrentUserName() = prefs?.getString(CURRENT_USER, "")

    fun setCurrentUserName(username: String) {
        prefs?.edit(commit = true) {
            putString(CURRENT_USER, username)
        }
    }
    fun getCurrentUserId() = prefs?.getInt(CURRENT_USER_ID, 0)
    fun setCurrentUserId(id: Int){
        prefs?.edit(commit = true) {
            putInt(CURRENT_USER_ID, id)
        }
    }


}