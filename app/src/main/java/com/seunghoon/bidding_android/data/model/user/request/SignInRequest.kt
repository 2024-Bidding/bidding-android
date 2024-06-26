package com.seunghoon.bidding_android.data.model.user.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SignInRequest(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
)
