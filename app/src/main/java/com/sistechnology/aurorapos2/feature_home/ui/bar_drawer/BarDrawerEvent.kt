package com.sistechnology.aurorapos2.feature_home.ui.bar_drawer

import com.sistechnology.aurorapos2.core.ui.components.MenuItem

sealed class BarDrawerEvent {
    object LogoutEvent : BarDrawerEvent()
    object NavigateToSettings : BarDrawerEvent()
    data class ToggleLogoutDialog(val show: Boolean) : BarDrawerEvent()
    data class NavigationDrawerItemSelected(val menuItem: MenuItem) : BarDrawerEvent()
}