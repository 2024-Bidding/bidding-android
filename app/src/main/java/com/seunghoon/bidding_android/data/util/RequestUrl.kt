package com.seunghoon.bidding_android.data.util

internal sealed class RequestUrl(val path: String) {
    data object User : RequestUrl(path = "/users") {
        val signIn = "${this.path}/login"
        val signUp = "${this.path}/signup"
    }

    data object Items : RequestUrl(path = "/items") {

    }

    data object File : RequestUrl(path = "/files") {
        val presignedUrl = "${this.path}/pre-signed"
    }
}
