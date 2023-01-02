package com.mate.carpool.ui.fragment.us7

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.mate.carpool.R
import com.mate.carpool.data.utils.SettingToolbarUtils.showBottomSheetFragment
import com.mate.carpool.data.vm.ReserveDriverViewModel
import com.mate.carpool.databinding.BottomSheetReservePassengerBinding
import com.mate.carpool.ui.binder.BindBottomSheetDialogFragment

class ReservePassengerFragment: BindBottomSheetDialogFragment<BottomSheetReservePassengerBinding>(R.layout.bottom_sheet_reserve_passenger) {
    private val reserveDriverViewModel: ReserveDriverViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.reserveDriverViewModel=reserveDriverViewModel
        binding.lifecycleOwner=viewLifecycleOwner

        showBottomSheetFragment(requireActivity(),"예약을 취소 하시겠어요?.","반복적이고 고의적인 카풀 예약 취소는 추후 서비스 이용에 제한됩니다.","운행종료")
    }
}