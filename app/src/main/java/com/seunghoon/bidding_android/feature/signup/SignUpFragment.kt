package com.seunghoon.bidding_android.feature.signup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.seunghoon.bidding_android.common.showToast
import com.seunghoon.bidding_android.databinding.FragmentSignUpBinding
import com.seunghoon.bidding_android.navigation.navigateToSignIn
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment() {

    private val signUpViewModel: SignUpViewModel by viewModel()

    private lateinit var binding: FragmentSignUpBinding

    private var imageUri: Uri? = null

    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater)
        collectSignUpSideEffect()
        setPictureListener()
        setDoSignInTextEvent()
        setSignUpButtonEvent()
        return binding.root
    }

    private fun collectSignUpSideEffect() {
        signUpViewModel.collectSideEffect {
            when (it) {
                is SignUpSideEffect.Success -> {
                    requireContext().showToast("회원가입 되었습니다! 로그인 후 이용해주세요")
                    navController.navigateToSignIn()
                }

                is SignUpSideEffect.EmailAlreadyExists -> {
                    requireContext().showToast("이미 존재하는 이메일이에요")
                }

                is SignUpSideEffect.SuccessFileUpload -> {
                    with(binding) {
                        signUpViewModel.signUp(
                            email = etSignUpEmail.text.toString(),
                            name = etSignUpName.text.toString(),
                            password = etSignUpPassword.text.toString(),
                            profileImageUrl = it.profileImageUrl,
                        )
                    }
                }
            }
        }
    }

    private fun setDoSignInTextEvent() = with(binding) {
        tvSignUpDoSignIn.setOnClickListener {
            navController.navigateToSignIn()
        }
    }

    private fun setPictureListener() = with(binding) {
        val launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                result.data?.data?.run {
                    imageUri = this
                    binding.imgSignUpProfile.visibility = View.VISIBLE
                    Glide.with(requireContext()).load(this).into(binding.imgSignUpProfile)
                }
            }

        cardViewSignUpProfile.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).run {
                setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                action = Intent.ACTION_PICK
                launcher.launch(this)
            }
        }
    }

    private fun setSignUpButtonEvent() = with(binding) {
        btnSignInSignIn.setOnClickListener {
            if (imageUri == null) {
                with(binding) {
                    signUpViewModel.signUp(
                        email = etSignUpEmail.text.toString(),
                        name = etSignUpName.text.toString(),
                        password = etSignUpPassword.text.toString(),
                        profileImageUrl = "https://static.vecteezy.com/system/resources/thumbnails/009/292/244/small/default-avatar-icon-of-social-media-user-vector.jpg",
                    )
                }
            } else {
                signUpViewModel.uploadImage(
                    uri = imageUri!!,
                    context = requireContext(),
                )
            }
        }
    }
}
