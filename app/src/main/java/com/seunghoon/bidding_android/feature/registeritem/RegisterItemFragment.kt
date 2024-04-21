package com.seunghoon.bidding_android.feature.registeritem

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.seunghoon.bidding_android.databinding.FragmentRegisterItemBinding
import java.time.LocalDateTime

class RegisterItemFragment : Fragment() {

    private lateinit var binding: FragmentRegisterItemBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = with(FragmentRegisterItemBinding.inflate(inflater)) {
        setDateListener(etRegisterItemStartDate)
        setDateListener(etRegisterItemEndDate)
        setTimeListener(etRegisterItemStartTime)
        setTimeListener(etRegisterItemEndTime)
        root
    }

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
}
