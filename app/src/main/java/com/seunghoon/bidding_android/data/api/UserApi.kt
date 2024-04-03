package com.seunghoon.bidding_android.data.api

import com.seunghoon.bidding_android.data.di.ktorClient
import com.seunghoon.bidding_android.data.model.user.request.SignInRequest
import com.seunghoon.bidding_android.data.model.user.request.SignUpRequest
import com.seunghoon.bidding_android.data.model.user.response.SignInResponse
import com.seunghoon.bidding_android.data.util.RequestUrl
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url

internal class UserApi {
    suspend fun signIn(signInRequest: SignInRequest): SignInResponse = with(ktorClient) {
        return post {
            url(RequestUrl.User.signIn)
            setBody(signInRequest)
        }.body<SignInResponse>()
    }

    suspend fun signUp(signUpRequest: SignUpRequest): Unit = with(ktorClient) {
        return post {
            url(RequestUrl.User.signUp)
            setBody(signUpRequest)
        }.body()
    }
}
