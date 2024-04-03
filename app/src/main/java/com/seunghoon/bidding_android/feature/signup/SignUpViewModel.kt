package com.seunghoon.bidding_android.feature.signup

import androidx.lifecycle.viewModelScope
import com.seunghoon.bidding_android.data.api.UserApi
import com.seunghoon.bidding_android.data.model.user.request.SignUpRequest
import com.seunghoon.bidding_android.data.util.Conflict
import com.seunghoon.bidding_android.feature.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class SignUpViewModel(
    private val userApi: UserApi,
) : BaseViewModel<Unit, SignUpSideEffect>(Unit) {
    fun signUp(
        email: String,
        name: String,
        password: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                userApi.signUp(
                    SignUpRequest(
                        email = email,
                        password = password,
                        name = name,
                    )
                )
            }.onSuccess {
                postSideEffect(SignUpSideEffect.Success)
            }.onFailure {
                when (it) {
                    is Conflict -> {
                        postSideEffect(SignUpSideEffect.EmailAlreadyExists)
                    }
                }
            }
        }
    }
}

internal sealed interface SignUpSideEffect {
    data object Success : SignUpSideEffect
    data object EmailAlreadyExists : SignUpSideEffect
}
