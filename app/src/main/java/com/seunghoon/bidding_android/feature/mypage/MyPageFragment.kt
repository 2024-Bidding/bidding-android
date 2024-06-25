package com.seunghoon.bidding_android.feature.mypage

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.seunghoon.bidding_android.R
import com.seunghoon.bidding_android.common.insertIntoGlide
import com.seunghoon.bidding_android.common.showToast
import com.seunghoon.bidding_android.databinding.FragmentMyPageBinding
import com.seunghoon.bidding_android.domain.entity.items.ItemsEntity
import com.seunghoon.bidding_android.navigation.navigateToSignIn
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPageFragment : Fragment() {

    private val binding by lazy {
        FragmentMyPageBinding.inflate(layoutInflater)
    }

    private val viewModel: MyPageViewModel by viewModel()

    private lateinit var myItems: List<ItemsEntity.ItemEntity>

    private lateinit var myBidItems: List<ItemsEntity.ItemEntity>

    private val myItemsAdapter by lazy {
        MyItemsAdapter(
            items = myItems,
            navController = navController,
        )
    }

    private val myBidItemsAdapter by lazy {
        MyItemsAdapter(
            items = myBidItems,
            navController = navController,
        )
    }

    private val navController by lazy {
        requireActivity().findNavController(R.id.container_main)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        collectMyPageSideEffect()
        initView()
        with(viewModel) {
            fetchUserInformation()
            fetchMyBidItems()
            fetchMyItems()
        }
        return binding.root
    }

    private fun initView() {
        setLogoutButton()
    }

    private fun setLogoutButton() {
        binding.btnMyPageLogout.setOnClickListener {
            val dialog = Dialog(requireContext()).apply {
                setContentView(R.layout.dialog_logout)
                window?.setLayout(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT,
                )

                findViewById<TextView>(R.id.tv_logout_yes).setOnClickListener {
                    navController.navigateToSignIn()
                    this@MyPageFragment.requireContext().showToast("로그아웃 되었습니다!")
                    hide()
                }

                findViewById<TextView>(R.id.tv_logout_no).setOnClickListener {
                    hide()
                }
            }
            dialog.show()
        }
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
                    if (it.items.items.isEmpty()) {
                        binding.tvMyPageItems.visibility = View.VISIBLE
                        binding.rvMyPageItems.visibility = View.VISIBLE
                    }
                    myBidItems = it.items.items
                    binding.rvMyPageItemsBid.adapter = myBidItemsAdapter
                }

                is MyPageSideEffect.SuccessFetchMyItems -> {
                    if (it.items.items.isEmpty()) {
                        binding.tvMyPageItemsBid.visibility = View.VISIBLE
                        binding.rvMyPageItemsBid.visibility = View.VISIBLE
                    }
                    myItems = it.items.items
                    binding.rvMyPageItems.adapter = myItemsAdapter
                }
            }
        }
    }
}
