package com.mate.carpool.ui.screen.reservePassenger

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mate.carpool.R
import com.mate.carpool.databinding.ItemviewPassengerInfoPopupBinding
import com.mate.carpool.ui.base.BasePopUpDialogFragment
import com.mate.carpool.ui.screen.reserveDriver.vm.ReserveDriverViewModel

class TicketPassengerPopUp(
    location: IntArray,
    private val onReportClick: () -> Unit,
) : BasePopUpDialogFragment<ItemviewPassengerInfoPopupBinding>(
    location,
    R.layout.itemview_passenger_info_popup
) {
    private val reserveDriverViewModel: ReserveDriverViewModel by activityViewModels()

    override fun bindFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ItemviewPassengerInfoPopupBinding =
        ItemviewPassengerInfoPopupBinding.inflate(inflater, container, false)

    override fun initView() {
        binding.tvReport.setOnClickListener {
            onReportClick()
            dismissAllowingStateLoss()
        }
    }

    override fun subScribeUi() {
    }
}