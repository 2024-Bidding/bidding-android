package com.seunghoon.bidding_android.feature.registeritem

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.seunghoon.bidding_android.databinding.FragmentRegisterItemBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime

class RegisterItemFragment : Fragment() {

    private lateinit var binding: FragmentRegisterItemBinding

    private val registerItemViewModel: RegisterItemViewModel by viewModel()

    private val navController by lazy {
        findNavController()
    }

    private lateinit var registerImageAdapter: RegisterImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = with(FragmentRegisterItemBinding.inflate(inflater)) {
        binding = this
        binding.viewModel = registerItemViewModel

        setImageRecyclerView()

        setPictureListener()
        setDateListener(etRegisterItemStartDate)
        setDateListener(etRegisterItemEndDate)
        setTimeListener(etRegisterItemStartTime)
        setTimeListener(etRegisterItemEndTime)
        handleRegisterItemSideEffect()

        root
    }

    private fun setImageRecyclerView() {
        registerImageAdapter = RegisterImageAdapter(mutableListOf())
        binding.rvImages.adapter = registerImageAdapter
    }

    private fun setPictureListener() = with(binding) {
        val launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val files = result.data?.clipData
                binding.rvImages.visibility = View.VISIBLE
                files?.run {
                    repeat(this.itemCount) { index ->
                        val uri = getItemAt(index).uri
                        registerItemViewModel.addUri(
                            context = requireContext(),
                            uri = uri,
                        )
                        registerImageAdapter.addImage(uri)
                        registerImageAdapter.notifyItemInserted(registerImageAdapter.itemCount - 1)
                    }
                }
            }

        cardViewRegisterItem.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).run {
                setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                action = Intent.ACTION_PICK
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                launcher.launch(this)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDateListener(view: EditText) {
        view.setOnClickListener {
            DatePickerDialog(requireContext()).run {
                show()
                setOnDateSetListener { _, year, month, day ->
                    view.setText("$year.${month + 1}.$day")
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTimeListener(view: EditText) {
        val currentTime = LocalDateTime.now()
        view.setOnClickListener {
            TimePickerDialog(requireContext(), { timePicker, hour, minute ->
                view.setText("$hour:${minute.toString().padStart(2, '0')}")
            }, currentTime.hour, currentTime.minute, false).run {
                show()
            }
        }
    }

    private fun handleRegisterItemSideEffect() {
        registerItemViewModel.collectSideEffect {
            when (it) {
                is RegisterItemSideEffect.SuccessCreatePresignedUrl -> {
                    with(binding) {
                        registerItemViewModel.registerItem(
                            name = etRegisterItemTitle.text.toString(),
                            endPrice = etRegisterItemEndPrice.text.toString(),
                            startPrice = etRegisterItemStartPrice.text.toString(),
                            startTime = etRegisterItemStartTime.text.toString(),
                            endTime = etRegisterItemEndTime.text.toString(),
                            imageUrls = it.filePath,
                            startDate = etRegisterItemStartDate.text.toString(),
                            endDate = etRegisterItemEndDate.text.toString(),
                        )
                        registerItemViewModel.uploadFile(presignedUrls = it.presignedUrls)
                    }
                }

                is RegisterItemSideEffect.Success -> {
                    Toast.makeText(context, "물품이 성공적으로 등록되었어요!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }

                is RegisterItemSideEffect.Failure -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
