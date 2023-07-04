package com.sistechnology.aurorapos2.core.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.sistechnology.aurorapos2.core.domain.company.CompanyInfo
import com.sistechnology.aurorapos2.feature_settings.domain.models.PrintingDeviceInfo

class SharedPreferencesHelper {
    companion object {

        private var prefs: SharedPreferences? = null
        private val gson: Gson = Gson()
        private var IS_FIRST_START = "isFirstStart"
        private var CURRENT_USER = "currentUser"
        private var CURRENT_USER_ID = "currentUserId"
        private var PRINTING_DEVICE_INFO = "printingDeviceInfo"
        private var COMPANY_INFO = "companyInfo"
        private var CURRENT_CLOSURE = "currentClosure"
        private var CLEAR_BASKET = "clearBasket"


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

    fun setPrintingDeviceInfo(printingDeviceInfo: PrintingDeviceInfo){
        prefs?.edit(commit = true){
            putString(PRINTING_DEVICE_INFO, gson.toJson(printingDeviceInfo))
        }
    }

    fun getPrintingDeviceInfo() : PrintingDeviceInfo{
        val infoString = prefs?.getString(PRINTING_DEVICE_INFO, "")
        if(infoString?.isEmpty()!=false){
            return PrintingDeviceInfo()
        }
        return gson.fromJson(infoString, PrintingDeviceInfo::class.java)
    }

    fun setCompanyInfo(companyInfo: CompanyInfo){
        prefs?.edit(commit = true){
            putString(COMPANY_INFO, gson.toJson(companyInfo))
        }
    }

    fun getCompanyInfo() : CompanyInfo{
        val infoString = prefs?.getString(COMPANY_INFO, "")
        if(infoString?.isEmpty()!=false){
            return CompanyInfo()
        }
        return gson.fromJson(infoString, CompanyInfo::class.java)
    }

    fun getCurrentClosure()  = prefs?.getInt(CURRENT_CLOSURE, 0)
    fun setCurrentClosure(closure: Int){
        prefs?.edit(commit = true){
            putInt(CURRENT_CLOSURE, closure)
        }
    }

    fun getClearBasket()  = prefs?.getBoolean(CLEAR_BASKET, false)
    fun setClearBasket(clearBasket: Boolean){
        prefs?.edit(commit = true){
            putBoolean(CLEAR_BASKET, clearBasket)
        }
    }








}