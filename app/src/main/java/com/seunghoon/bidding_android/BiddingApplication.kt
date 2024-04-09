package com.seunghoon.bidding_android

import android.app.Application
import com.seunghoon.bidding_android.data.api.ItemApi
import com.seunghoon.bidding_android.data.api.UserApi
import com.seunghoon.bidding_android.feature.items.ItemsViewModel
import com.seunghoon.bidding_android.feature.signin.SignInViewModel
import com.seunghoon.bidding_android.feature.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class BiddingApplication : Application() {

    private val apiModule = module {
        single { UserApi() }
        single { ItemApi() }
    }

    private val viewModelModule = module {
        viewModel { SignInViewModel(userApi = get()) }
        viewModel { SignUpViewModel(userApi = get()) }
        viewModel { ItemsViewModel(itemApi = get()) }
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


