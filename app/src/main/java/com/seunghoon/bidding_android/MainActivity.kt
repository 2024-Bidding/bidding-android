package com.seunghoon.bidding_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.createGraph
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.fragment
import com.seunghoon.bidding_android.databinding.ActivityMainBinding
import com.seunghoon.bidding_android.feature.splash.SplashFragment
import com.seunghoon.bidding_android.navigation.NavigationRoute

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view_main)
        val navController = navHostFragment?.findNavController()

        navController?.createGraph(startDestination = NavigationRoute.Auth.SPLASH) {
            fragment<SplashFragment>(route = NavigationRoute.Auth.SPLASH) {
                label = NavigationRoute.Auth.SPLASH
            }
        }
    }
}
