package com.seunghoon.bidding_android.data.util

internal sealed class RequestUrl(val path: String) {
    data object User : RequestUrl(path = "/users") {
        val signIn = "${this.path}/login"
        val signUp = "${this.path}/signup"
        val my = "${this.path}/my"
    }

    data object Item : RequestUrl(path = "/items") {
        val details = { itemId: Long ->
            "${this.path}/$itemId"
        }
    }

    data object Bid : RequestUrl(path = "/bid") {
        val bidItem = { itemId: Long ->
            "${this.path}/$itemId"
        }
    }

    data object File : RequestUrl(path = "/files") {
        val presignedUrl = "${this.path}/pre-signed"
    }
}
