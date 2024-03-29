package com.sistechnology.aurorapos2.core.ui

sealed class Screen(val route: String) {
    object HomeScreen : Screen(route = "home_screen") {
    }

    object UsersScreen : Screen(route = "users_screen") {

    }

    object PaymentScreen : Screen(route = "payment_screen") {

    }
    object SettingsScreen : Screen(route = "settings_screen"){

    }
    object ClientsScreen : Screen(route = "clients_screen"){

    }

}
