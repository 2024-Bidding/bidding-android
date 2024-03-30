package com.seunghoon.bidding_android.navigation

sealed class NavigationRoute(
    val route: String,
) {
    data object Auth: NavigationRoute(route = "auth") {
        val SPLASH = "$route/splash"
        val SIGN_IN = "$route/signIn"
        val SIGN_UP = "$route/signUp"
    }
}
