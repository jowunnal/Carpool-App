package com.mate.carpool.ui.fragment.us7

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import com.mate.carpool.data.utils.LayoutParamsUtils.getBottomSheetDialogDefaultWidth
import com.mate.carpool.databinding.ItemviewPassengerInfoPopupBinding

class TicketPassengerPopUp(private val location:IntArray) : DialogFragment() {
    private var _binding: ItemviewPassengerInfoPopupBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= ItemviewPassengerInfoPopupBinding.inflate(inflater,container,false)

        dialog?.window?.apply {
            requestFeature(Window.FEATURE_NO_TITLE)
            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            setGravity(Gravity.TOP or Gravity.START)
            dialog?.window?.attributes!!.x= location[0] - getBottomSheetDialogDefaultWidth(18,requireActivity())
            dialog?.window?.attributes!!.y= location[1]
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvFire.setOnClickListener {
            dismiss()
        }
        binding.tvReport.setOnClickListener {
            dismiss()
        }
    }

}