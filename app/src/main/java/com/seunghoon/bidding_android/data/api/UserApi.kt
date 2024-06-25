package com.seunghoon.bidding_android.data.api

import com.seunghoon.bidding_android.data.di.ktorClient
import com.seunghoon.bidding_android.data.model.user.request.SignInRequest
import com.seunghoon.bidding_android.data.model.user.request.SignUpRequest
import com.seunghoon.bidding_android.data.model.user.response.SignInResponse
import com.seunghoon.bidding_android.data.model.user.response.UserResponse
import com.seunghoon.bidding_android.data.util.LocalStorage
import com.seunghoon.bidding_android.data.util.RequestHandler
import com.seunghoon.bidding_android.data.util.RequestUrl
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url

internal class UserApi(
    private val localStorage: LocalStorage,
) {
    suspend fun signIn(signInRequest: SignInRequest): SignInResponse = with(ktorClient) {
        return RequestHandler<SignInResponse>().request {
            post {
                url(RequestUrl.User.signIn)
                setBody(signInRequest)
            }.body<SignInResponse>()
        }
    }

    suspend fun signUp(signUpRequest: SignUpRequest): Unit = with(ktorClient) {
        return RequestHandler<Unit>().request {
            post {
                url(RequestUrl.User.signUp)
                setBody(signUpRequest)
            }.body()
        }
    }

    suspend fun fetchUserInformation() = runCatching {
        RequestHandler<UserResponse>().request {
            ktorClient.get {
                url(RequestUrl.User.my)
                header(
                    key = "Authorization",
                    value = localStorage.getValue(LocalStorage.ACCESS_TOKEN)
                )
            }.body<UserResponse>()
        }
    }
}
