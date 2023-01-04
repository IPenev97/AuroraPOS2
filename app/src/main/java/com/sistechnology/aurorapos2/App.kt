package com.sistechnology.aurorapos2

import android.app.Application
import android.content.SharedPreferences
import com.sistechnology.aurorapos2.core.utils.PresetDataHelper
import com.sistechnology.aurorapos2.core.utils.SharedPreferencesHelper
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var presetDataHelper: PresetDataHelper

    @Inject
    lateinit var prefs: SharedPreferencesHelper

    override fun onCreate() {
        super.onCreate()
        if(prefs.getIsFirstRun() == true) {
            MainScope().launch {
                presetDataHelper.populateDatabase()
            }
            prefs.setIsFirstRun(false)
        }

    }
}