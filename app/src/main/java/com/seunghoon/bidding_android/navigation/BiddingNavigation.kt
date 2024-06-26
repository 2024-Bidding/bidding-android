package com.seunghoon.bidding_android.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.seunghoon.bidding_android.R

object NavArguments {
    const val ITEM_ID = "item_id"
}

internal fun NavController.navigateToSignIn() {
    navigate(R.id.navigate_to_sign_in)
}

internal fun NavController.navigateToSignUp() {
    navigate(R.id.navigate_to_sign_up)
}

internal fun NavController.navigateToItemDetails(itemId: Long) {
    val bundle = bundleOf(NavArguments.ITEM_ID to itemId)
    navigate(R.id.item_details, bundle)
}

internal fun NavController.navigateToRoot() {
    navigate(R.id.navigate_to_root)
}

internal fun NavController.navigateToCreateItem() {
    navigate(R.id.navigate_to_create_item)
}

internal fun NavController.navigateToSearch() {
    navigate(R.id.navigate_to_search)
}
