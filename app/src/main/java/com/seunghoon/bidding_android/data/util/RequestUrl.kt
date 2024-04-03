package com.seunghoon.bidding_android.data.util

internal sealed class RequestUrl(val path: String) {
    data object User : RequestUrl(path = "/users") {
        val signIn = "${this.path}/login"
        val signUp = "${this.path}/signup"
    }
}
