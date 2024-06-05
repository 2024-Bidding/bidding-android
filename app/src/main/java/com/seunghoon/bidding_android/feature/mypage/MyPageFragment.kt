package com.seunghoon.bidding_android.feature.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.seunghoon.bidding_android.databinding.FragmentMyPageBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPageFragment : Fragment() {

    private val binding by lazy {
        FragmentMyPageBinding.inflate(layoutInflater)
    }

    private val viewModel: MyPageViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}
