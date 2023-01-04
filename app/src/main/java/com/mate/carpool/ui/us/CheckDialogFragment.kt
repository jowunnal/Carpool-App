package com.mate.carpool.ui.us

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.mate.carpool.ui.us.reserveDriver.vm.ReserveDriverViewModel
import com.mate.carpool.databinding.DialogCheckBinding

class CheckDialogFragment(private val titleText:String,private val messageText:String) :DialogFragment() {
    private var _binding:DialogCheckBinding ?= null
    private val binding get() = _binding!!
    private val reserveDriverViewModel: ReserveDriverViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=DialogCheckBinding.inflate(inflater,container,false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.title.text=titleText
        binding.message.text=messageText

        binding.positiveButton.setOnClickListener {
            when(titleText){
                "티켓을 삭제하시겠어요?"->reserveDriverViewModel.updateTicketStatus("CANCEL")
                "운행을 종료합니다."->reserveDriverViewModel.updateTicketStatus("AFTER")
                "예약을 취소 하시겠어요?."->reserveDriverViewModel.updateTicketStatus("BEFORE")
            }
            dismiss()
        }
        binding.negativeButton.setOnClickListener {
            dismiss()
        }
    }
}