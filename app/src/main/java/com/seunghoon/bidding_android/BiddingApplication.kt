package com.seunghoon.bidding_android

import android.app.Application
import com.seunghoon.bidding_android.data.api.FileApi
import com.seunghoon.bidding_android.data.api.ItemApi
import com.seunghoon.bidding_android.data.api.UserApi
import com.seunghoon.bidding_android.data.util.LocalStorage
import com.seunghoon.bidding_android.feature.details.ItemDetailsViewModel
import com.seunghoon.bidding_android.feature.items.ItemsViewModel
import com.seunghoon.bidding_android.feature.mypage.MyPageViewModel
import com.seunghoon.bidding_android.feature.registeritem.CreateItemViewModel
import com.seunghoon.bidding_android.feature.signin.SignInViewModel
import com.seunghoon.bidding_android.feature.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class BiddingApplication : Application() {

    private val apiModule = module {
        single { UserApi(localStorage = get()) }
        single { ItemApi(localStorage = get()) }
        single { FileApi() }
        single { LocalStorage(context = applicationContext) }
    }

    private val viewModelModule = module {
        viewModel {
            SignInViewModel(
                userApi = get(),
                localStorage = get(),
            )
        }
        viewModel { SignUpViewModel(userApi = get()) }
        viewModel { ItemsViewModel(itemApi = get()) }
        viewModel {
            CreateItemViewModel(
                itemApi = get(),
                fileApi = get(),
            )
        }
        viewModel {
            ItemDetailsViewModel(itemApi = get())
        }
        viewModel {
            MyPageViewModel(userApi = get())
        }
    }

    private val biddingModule = module {
        includes(
            apiModule,
            viewModelModule,
        )
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(biddingModule)
        }
    }
}


