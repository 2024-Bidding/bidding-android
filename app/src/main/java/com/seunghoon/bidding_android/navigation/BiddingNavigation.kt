package com.seunghoon.bidding_android.navigation

import androidx.navigation.NavController
import com.seunghoon.bidding_android.R

internal fun NavController.navigateToSignIn() {
    navigate(R.id.navigate_to_sign_in)
}

internal fun NavController.navigateToSignUp() {
    navigate(R.id.navigate_to_sign_up)
}

internal fun NavController.navigateToItems() {
    navigate(R.id.navigate_to_items)
}

internal fun NavController.navigateToRegisterItem() {
    navigate(R.id.navigate_to_register_item)
}
