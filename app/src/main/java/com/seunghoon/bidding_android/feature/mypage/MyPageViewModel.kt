package com.seunghoon.bidding_android.feature.mypage

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.seunghoon.bidding_android.data.api.UserApi
import com.seunghoon.bidding_android.data.model.user.response.UserResponse
import com.seunghoon.bidding_android.feature.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class MyPageViewModel(
    private val userApi: UserApi,
) : BaseViewModel<Unit, MyPageSideEffect>(Unit) {

    init {
        fetchUserInformation()
    }

    private fun fetchUserInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            userApi.fetchUserInformation().onSuccess {
                postSideEffect(MyPageSideEffect.Success(it))
            }.onFailure {
                Log.d("TEST", it.toString())
            }
        }
    }

}

sealed interface MyPageSideEffect {
    data class Success(val response: UserResponse): MyPageSideEffect
}
