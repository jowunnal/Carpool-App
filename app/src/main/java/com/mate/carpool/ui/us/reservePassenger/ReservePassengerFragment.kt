package com.mate.carpool.ui.us.reservePassenger

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mate.carpool.R
import com.mate.carpool.data.utils.LayoutParamsUtils
import com.mate.carpool.data.utils.SettingToolbarUtils.showBottomSheetFragment
import com.mate.carpool.databinding.BottomSheetReservePassengerBinding
import com.mate.carpool.ui.binder.BindBottomSheetDialogFragment
import com.mate.carpool.ui.us.reserveDriver.vm.ReserveDriverViewModel

class ReservePassengerFragment: BindBottomSheetDialogFragment<BottomSheetReservePassengerBinding>(R.layout.bottom_sheet_reserve_passenger) {
    private val reserveDriverViewModel: ReserveDriverViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.reserveDriverViewModel=reserveDriverViewModel
        binding.lifecycleOwner=viewLifecycleOwner

        binding.btnCancel.setOnClickListener {
            showBottomSheetFragment(requireActivity(),"예약을 취소 하시겠어요?.","반복적이고 고의적인 카풀 예약 취소는 추후 서비스 이용에 제한됩니다.","예약취소")
        }

        reserveDriverViewModel.ticketID.observe(viewLifecycleOwner, Observer {
            reserveDriverViewModel.getTicketDetailFromId()
        })

        binding.imageX.setOnClickListener {
            dismiss()
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(),R.style.AppBottomSheetDialogTheme).apply {
            behavior.peekHeight=
                LayoutParamsUtils.getBottomSheetDialogDefaultHeight(93, requireActivity())
        }
        return dialog
    }
}