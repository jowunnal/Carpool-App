package com.mate.carpool.ui.screen.reservePassenger

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mate.carpool.R
import com.mate.carpool.ui.utils.SettingToolbarUtils.showBottomSheetFragment
import com.mate.carpool.databinding.BottomSheetReservePassengerBinding
import com.mate.carpool.ui.base.BindBottomSheetDialogFragment
import com.mate.carpool.ui.widget.listener.OnItemClickListener
import com.mate.carpool.ui.screen.reserveDriver.adapterview.ReserveDriverViewAdapter
import com.mate.carpool.ui.screen.reserveDriver.vm.ReserveDriverViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReservePassengerFragment(
    private val studentNumber:String
    ): BindBottomSheetDialogFragment<BottomSheetReservePassengerBinding>(R.layout.bottom_sheet_reserve_passenger) {
    private val reserveDriverViewModel: ReserveDriverViewModel by activityViewModels()
    @Inject lateinit var reserveDriverViewAdapter:ReserveDriverViewAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.reserveDriverViewModel=reserveDriverViewModel
        binding.lifecycleOwner=viewLifecycleOwner

        binding.recyclerviewPassengers.apply {
            adapter=reserveDriverViewAdapter
            layoutManager= LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
        }

        reserveDriverViewAdapter.setItemClickListener(object : OnItemClickListener {
            override fun setOnItemClickListener(view: View, pos: Int) {
                if(!requireActivity().isFinishing){
                    val location = IntArray(2)
                    view.getLocationOnScreen(location)
                    reserveDriverViewModel.passengerId.value = reserveDriverViewAdapter.getPassengerIdOnSelectedItem(pos).toInt()
                    TicketPassengerPopUp(location).show(requireActivity().supportFragmentManager,"popup")
                }
            }
        })

        reserveDriverViewModel.ticketDetail.value.let {
            if(it.passenger!=null){
                val items:ArrayList<HashMap<String,String>> = arrayListOf()
                for(item in it.passenger){
                    val hashItem = hashMapOf<String,String>()
                    hashItem["id"] = item.studentID
                    hashItem["passengerId"] = item.passengerId.toString()
                    hashItem["name"] = item.name
                    hashItem["profile"]=item.studentProfile.toString()
                    items.add(hashItem)
                }
                reserveDriverViewAdapter.setItems(items)
                reserveDriverViewAdapter.notifyDataSetChanged()
            }
        }

        binding.btnCancel.setOnClickListener {
            reserveDriverViewModel.setTicketPassengerId(studentNumber)
            showBottomSheetFragment(requireActivity(),"예약을 취소 하시겠어요?.","반복적이고 고의적인 카풀 예약 취소는 추후 서비스 이용에 제한됩니다.","예약취소")
        }

        binding.imageX.setOnClickListener {
            dismiss()
        }

        binding.iconDriverHamburger.setOnClickListener {
            if(!requireActivity().isFinishing){
                val location = IntArray(2)
                it.getLocationOnScreen(location)
                TicketPassengerPopUp(location).show(requireActivity().supportFragmentManager,"popup")
            }
        }
    }
}