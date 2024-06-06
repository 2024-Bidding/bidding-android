package com.seunghoon.bidding_android.data.model.user.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SignUpRequest (
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
    @SerialName("name") val name: String,
    @SerialName("profile_image_url") val profileImageUrl: String,
)
