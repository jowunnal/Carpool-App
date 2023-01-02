package com.mate.carpool.ui.fragment.us7

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mate.carpool.R
import com.mate.carpool.data.utils.LayoutParamsUtils.getBottomSheetDialogDefaultHeight
import com.mate.carpool.data.utils.SettingToolbarUtils
import com.mate.carpool.data.vm.ReserveDriverViewModel
import com.mate.carpool.databinding.BottomSheetReserveDriverBinding
import com.mate.carpool.ui.adapter.us7.ReserveDriverViewAdapter
import com.mate.carpool.ui.binder.BindBottomSheetDialogFragment
import com.mate.carpool.ui.listener.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ReserveDriverFragment : BindBottomSheetDialogFragment<BottomSheetReserveDriverBinding>(R.layout.bottom_sheet_reserve_driver) {
    private val reserveDriverViewModel:ReserveDriverViewModel by activityViewModels()
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
                    Log.d("test",pos.toString())
                    /*location[0]=view.x.toInt()
                    location[1]=view.y.toInt()*/
                    view.getLocationOnScreen(location)
                    Log.d("test",location[0].toString()+" "+location[1].toString())
                    TicketPassengerPopUp(location).show(requireActivity().supportFragmentManager,"popup")
                }
            }

        })

        reserveDriverViewModel.ticketDetail.observe(viewLifecycleOwner, Observer {
            val items:ArrayList<HashMap<String,String>> = arrayListOf()
            for(item in it.passengers!!){
                val hashItem = hashMapOf<String,String>()
                hashItem["name"]=item.memberName
                hashItem["profile"]=item.profileImage
                items.add(hashItem)
            }
            reserveDriverViewAdapter.setItems(items)
            reserveDriverViewAdapter.notifyDataSetChanged()
        })

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
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(),R.style.AppBottomSheetDialogTheme).apply {
            behavior.peekHeight=getBottomSheetDialogDefaultHeight(93,requireActivity())
        }
        return dialog
    }
}