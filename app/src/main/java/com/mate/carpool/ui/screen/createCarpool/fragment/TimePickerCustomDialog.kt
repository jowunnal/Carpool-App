package com.mate.carpool.ui.screen.createCarpool.fragment

import android.os.Bundle
import android.view.View
import com.mate.carpool.R
import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.databinding.DialogTimePickerBinding
import com.mate.carpool.ui.base.BaseBottomSheetDialogFragment
import com.mate.carpool.ui.screen.createCarpool.item.TimeUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimePickerCustomDialog(
    private val getTime: (TimeUiState) -> Unit,
) :BaseBottomSheetDialogFragment<DialogTimePickerBinding>(R.layout.dialog_time_picker) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        hour.apply {
            maxValue = 11
            minValue = 0
            value = 0
        }
        min.apply {
            maxValue = 59
            minValue = 0
        }
        dayStatus.apply {
            minValue = 0
            maxValue = 1
            displayedValues = DayStatus.values().map { it.displayName }.toTypedArray()
        }
        dayStatus.setOnValueChangedListener { picker, i, i2 ->
            when(picker.value) {
                0 -> {
                    hour.apply {
                        maxValue = 11
                        minValue = 0
                        value = 0
                    }
                }
                1 -> {
                    hour.apply {
                        maxValue = 23
                        minValue = 12
                        value = 12
                    }
                }
            }
        }
        btnConfirm.setOnClickListener {
            getTime(
                TimeUiState(
                    hour = hour.value,
                    min = min.value,
                    dayStatus = DayStatus.values()[dayStatus.value]
                )
            )
            dismissAllowingStateLoss()
        }
        exit.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }
}