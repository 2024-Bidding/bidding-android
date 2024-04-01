package com.seunghoon.bidding_android.data.model.user.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SignInResponse(
    @SerialName("access_token") val accessToken: String,
)
