package com.sistechnology.aurorapos2.feature_home.ui.bar_drawer

sealed class BarDrawerEvent {
    object LogoutEvent : BarDrawerEvent()
    object NavigateToSettings : BarDrawerEvent()
    data class ToggleLogoutDialog(val show: Boolean) : BarDrawerEvent()
}