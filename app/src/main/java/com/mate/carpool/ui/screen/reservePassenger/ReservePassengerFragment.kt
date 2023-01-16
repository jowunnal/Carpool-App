package com.mate.carpool.ui.screen.reservePassenger

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mate.carpool.R
import com.mate.carpool.databinding.BottomSheetReservePassengerBinding
import com.mate.carpool.ui.base.BaseBottomSheetDialogFragment
import com.mate.carpool.ui.screen.CheckDialogFragment
import com.mate.carpool.ui.screen.reserveDriver.adapterview.ReserveDriverViewAdapter
import com.mate.carpool.ui.screen.reserveDriver.vm.ReserveDriverViewModel
import com.mate.carpool.ui.utils.SettingToolbarUtils.showBottomSheetFragment
import com.mate.carpool.ui.widget.listener.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReservePassengerFragment(
    private val studentNumber:String,
    private val onRewNew : Renewing
    ): BaseBottomSheetDialogFragment<BottomSheetReservePassengerBinding>(R.layout.bottom_sheet_reserve_passenger) {
    private val reserveDriverViewModel: ReserveDriverViewModel by viewModels()
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
                    //reserveDriverViewModel.passengerId.value = reserveDriverViewAdapter.getPassengerIdOnSelectedItem(pos).toLong()
                    TicketPassengerPopUp(location).show(requireActivity().supportFragmentManager,"popup")
                }
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    reserveDriverViewModel.toastMessage.collectLatest {
                        Toast.makeText(requireActivity(),it, Toast.LENGTH_SHORT).show()
                    }
                }
                launch {
                    reserveDriverViewModel.ticketDetail.collectLatest {
                        if(it.passenger!=null){
                            val items:ArrayList<HashMap<String,String>> = arrayListOf()
                            for(item in it.passenger){
                                val hashItem = hashMapOf<String,String>()
                                hashItem["id"] = item.studentID
                                hashItem["passengerId"] = item.passengerId.toString()
                                hashItem["name"] = item.name
                                hashItem["profile"]=item.profile
                                items.add(hashItem)
                            }
                            reserveDriverViewAdapter.setItems(items)
                            reserveDriverViewAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }


        binding.btnCancel.setOnClickListener {
            reserveDriverViewModel.setTicketPassengerId(studentNumber)
            showBottomSheetFragment(
                requireActivity(),
                "예약을 취소 하시겠어요?.",
                "반복적이고 고의적인 카풀 예약 취소는 추후 서비스 이용에 제한됩니다.",
                "예약취소",
                onDoSomething = object : CheckDialogFragment.Listener() {
                    override fun onPositiveButtonClick() {
                        reserveDriverViewModel.deletePassengerToTicket()
                        onRewNew.onRewNew()
                    }
                }
            )
        }

        binding.imageX.setOnClickListener {
            dismissAllowingStateLoss()
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