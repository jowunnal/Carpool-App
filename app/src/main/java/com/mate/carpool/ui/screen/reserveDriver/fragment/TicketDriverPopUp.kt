package com.mate.carpool.ui.screen.reserveDriver.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mate.carpool.R
import com.mate.carpool.databinding.ItemviewDriverInfoPopupBinding
import com.mate.carpool.ui.base.BasePopUpDialogFragment
import com.mate.carpool.ui.screen.reserveDriver.vm.ReserveDriverViewModel

class TicketDriverPopUp(
    location: IntArray,
    private val onReportClick: () -> Unit,
) : BasePopUpDialogFragment<ItemviewDriverInfoPopupBinding>(
    location,
    R.layout.itemview_driver_info_popup
) {

    private val reserveDriverViewModel: ReserveDriverViewModel by activityViewModels()

    override fun bindFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ItemviewDriverInfoPopupBinding =
        ItemviewDriverInfoPopupBinding.inflate(inflater, container, false)

    override fun initView() = with(binding) {
        tvFire.setOnClickListener {
            reserveDriverViewModel.deletePassengerToTicket()
        }
        tvReport.setOnClickListener {
            onReportClick()
            dismissAllowingStateLoss()
        }
    }

    override fun subScribeUi() {
    }
}