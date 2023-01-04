package com.sistechnology.aurorapos2.feature_home.domain.use_case.bar_drawer

import com.sistechnology.aurorapos2.core.utils.SharedPreferencesHelper

class LogoutUseCase (
    val sharedPreferencesHelper: SharedPreferencesHelper
){
    operator fun invoke(){
        sharedPreferencesHelper.setCurrentUserName("")
    }
}