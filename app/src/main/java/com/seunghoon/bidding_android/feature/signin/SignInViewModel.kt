package com.seunghoon.bidding_android.feature.signin

import androidx.lifecycle.viewModelScope
import com.seunghoon.bidding_android.data.api.UserApi
import com.seunghoon.bidding_android.data.model.user.request.SignInRequest
import com.seunghoon.bidding_android.data.util.NotFound
import com.seunghoon.bidding_android.data.util.Unauthorized
import com.seunghoon.bidding_android.feature.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class SignInViewModel(
    private val userApi: UserApi,
) : BaseViewModel<Unit, SignInSideEffect>(Unit) {

    fun signIn(
        email: String,
        password: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                userApi.signIn(
                    signInRequest = SignInRequest(
                        email = email,
                        password = password,
                    )
                )
            }.onSuccess {
                postSideEffect(SignInSideEffect.Success("성공적으로 로그인되었습니다."))
            }.onFailure {
                when (it) {
                    is Unauthorized -> postSideEffect(SignInSideEffect.InvalidPassword("잘못된 비밀번호입니다."))
                    is NotFound -> postSideEffect(SignInSideEffect.NotFoundEmail("존재하지 않는 이메일입니다."))
                }
            }
        }
    }
}

internal sealed interface SignInSideEffect {
    data class Success(val message: String) : SignInSideEffect
    data class NotFoundEmail(val message: String) : SignInSideEffect
    data class InvalidPassword(val message: String) : SignInSideEffect
}
