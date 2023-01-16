package com.mate.carpool.ui.screen.reservePassenger

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mate.carpool.R
import com.mate.carpool.databinding.ItemviewPassengerInfoPopupBinding
import com.mate.carpool.ui.base.BasePopUpDialogFragment

class TicketPassengerPopUp(location:IntArray)
    : BasePopUpDialogFragment<ItemviewPassengerInfoPopupBinding>(location,R.layout.itemview_passenger_info_popup) {

    override fun bindFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ItemviewPassengerInfoPopupBinding = ItemviewPassengerInfoPopupBinding.inflate(inflater,container,false)

    override fun initView() {
        binding.tvReport.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }

    override fun subScribeUi() {
    }
}