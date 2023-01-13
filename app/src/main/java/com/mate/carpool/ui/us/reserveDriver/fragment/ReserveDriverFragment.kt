package com.mate.carpool.ui.us.reserveDriver.fragment

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mate.carpool.R
import com.mate.carpool.data.model.domain.UserModel
import com.mate.carpool.ui.utils.LayoutParamsUtils.getBottomSheetDialogDefaultHeight
import com.mate.carpool.ui.utils.SettingToolbarUtils
import com.mate.carpool.databinding.BottomSheetReserveDriverBinding
import com.mate.carpool.ui.us.reserveDriver.adapterview.ReserveDriverViewAdapter
import com.mate.carpool.ui.binder.BindBottomSheetDialogFragment
import com.mate.carpool.ui.listener.OnItemClickListener
import com.mate.carpool.ui.us.reserveDriver.vm.ReserveDriverViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ReserveDriverFragment() : BindBottomSheetDialogFragment<BottomSheetReserveDriverBinding>(R.layout.bottom_sheet_reserve_driver) {
    private val reserveDriverViewModel: ReserveDriverViewModel by activityViewModels()
    @Inject lateinit var reserveDriverViewAdapter: ReserveDriverViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.reserveDriverViewModel=reserveDriverViewModel
        binding.lifecycleOwner=viewLifecycleOwner

        binding.recyclerviewPassengers.apply {
            adapter=reserveDriverViewAdapter
            layoutManager=LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
        }

        reserveDriverViewAdapter.setItemClickListener(object : OnItemClickListener{
            override fun setOnItemClickListener(view: View, pos: Int) {
                if(!requireActivity().isFinishing){
                    val location = IntArray(2)
                    view.getLocationOnScreen(location)
                    reserveDriverViewModel.passengerId.value = reserveDriverViewAdapter.getPassengerIdOnSelectedItem(pos).toInt()
                    TicketDriverPopUp(location).show(requireActivity().supportFragmentManager,"popup")
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
            SettingToolbarUtils.showBottomSheetFragment(requireActivity(),"티켓을 삭제하시겠어요?","패신저에게 고지하셨나요? 이미 입금을 받으셨다면 환불처리를 진행해 주세요.\n" +
                    "패신저가 있는 상태에서 2회 이상 취소시, 추후 서비스를 더이상 이용하실 수 없습니다.\n" +
                    "그래도 삭제하시겠습니까?","티켓삭제")
        }

        binding.btnConfirm.setOnClickListener {
            SettingToolbarUtils.showBottomSheetFragment(requireActivity(),"운행을 종료합니다.","유료 운행의 경우 오전7시~9시, 오후 6시~8시 까지 운행을 종료해야 합니다.","운행종료")
        }

        binding.imageX.setOnClickListener {
            dismiss()
        }
        binding.imgTicketRightarrow.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(reserveDriverViewModel.ticketDetail.value?.openChatUrl))
            startActivity(intent)
        }
    }
}