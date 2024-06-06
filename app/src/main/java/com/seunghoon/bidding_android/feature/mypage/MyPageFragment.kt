package com.seunghoon.bidding_android.feature.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.seunghoon.bidding_android.common.insertIntoGlide
import com.seunghoon.bidding_android.databinding.FragmentMyPageBinding
import com.seunghoon.bidding_android.domain.entity.items.ItemsEntity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPageFragment : Fragment() {

    private val binding by lazy {
        FragmentMyPageBinding.inflate(layoutInflater)
    }

    private val viewModel: MyPageViewModel by viewModel()

    private lateinit var myItems: List<ItemsEntity.ItemEntity>

    private lateinit var myBidItems: List<ItemsEntity.ItemEntity>

    private val myItemsAdapter by lazy {
        MyItemsAdapter(items = myItems)
    }

    private val myBidItemsAdapter by lazy {
        MyItemsAdapter(items = myBidItems)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        collectMyPageSideEffect()
        with(viewModel) {
            fetchUserInformation()
            fetchMyBidItems()
            fetchMyItems()
        }
        return binding.root
    }

    private fun collectMyPageSideEffect() {
        viewModel.collectSideEffect {
            when (it) {
                is MyPageSideEffect.Success -> {
                    with(binding) {
                        tvMyPageName.text = it.response.name
                        tvMyPageEmail.text = it.response.email
                        context?.run {
                            insertIntoGlide(
                                context = this,
                                imageView = binding.imgMyPageProfile,
                                url = it.response.profileImageUrl,
                            )
                        }
                    }
                }

                is MyPageSideEffect.SuccessFetchMyBidItems -> {
                    myBidItems = it.items.items
                    binding.rvMyPageItemsBid.adapter = myBidItemsAdapter
                }

                is MyPageSideEffect.SuccessFetchMyItems -> {
                    myItems = it.items.items
                    binding.rvMyPageItems.adapter = myItemsAdapter
                }
            }
        }
    }
}
