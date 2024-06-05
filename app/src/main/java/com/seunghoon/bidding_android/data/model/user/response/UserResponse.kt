package com.seunghoon.bidding_android.data.model.user.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("email") val email: String,
    @SerialName("name") val name: String,
    @SerialName("profile_image_url") val profileImageUrl: String,
)
