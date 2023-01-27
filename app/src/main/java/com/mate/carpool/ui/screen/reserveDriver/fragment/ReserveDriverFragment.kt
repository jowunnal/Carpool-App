package com.mate.carpool.ui.screen.reserveDriver.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mate.carpool.R
import com.mate.carpool.data.model.domain.item.TicketStatus
import com.mate.carpool.data.model.domain.item.getTicketStatusDTO
import com.mate.carpool.databinding.BottomSheetReserveDriverBinding
import com.mate.carpool.ui.base.BaseBottomSheetDialogFragment
import com.mate.carpool.ui.screen.CheckDialogFragment
import com.mate.carpool.ui.screen.home.compose.HomeFragmentDirections
import com.mate.carpool.ui.screen.reserveDriver.adapterview.ReserveDriverViewAdapter
import com.mate.carpool.ui.screen.reserveDriver.vm.ReserveDriverViewModel
import com.mate.carpool.ui.util.SettingToolbarUtils
import com.mate.carpool.ui.widget.listener.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ReserveDriverFragment(
    private val onRewNew: () -> Unit
) : BaseBottomSheetDialogFragment<BottomSheetReserveDriverBinding>(R.layout.bottom_sheet_reserve_driver) {
    private val reserveDriverViewModel: ReserveDriverViewModel by activityViewModels()

    @Inject
    lateinit var reserveDriverViewAdapter: ReserveDriverViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.reserveDriverViewModel = reserveDriverViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.recyclerviewPassengers.apply {
            adapter = reserveDriverViewAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }

        reserveDriverViewModel.getMyTicket()

        reserveDriverViewAdapter.setItemClickListener(object : OnItemClickListener {
            override fun setOnItemClickListener(view: View, pos: Int) {
                if (!requireActivity().isFinishing) {
                    val location = IntArray(2)
                    view.getLocationOnScreen(location)
                    reserveDriverViewModel.passengerId.value =
                        reserveDriverViewAdapter.getPassengerIdOnSelectedItem(pos).toLong()
                    TicketDriverPopUp(location) {
                        val action = HomeFragmentDirections.actionHomeFragmentToReportFragment(
                            reserveDriverViewAdapter.getStudentIdOnSelectedItem(pos)
                        )
                        findNavController().navigate(action)
                        dismissAllowingStateLoss()
                    }.show(requireActivity().supportFragmentManager, "popup")
                }
            }
        })

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    reserveDriverViewModel.toastMessage.collectLatest {
                        if (it != "")
                            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                    }
                }
                launch {
                    reserveDriverViewModel.ticketDetail.collectLatest {
                        if (it.passenger != null) {
                            val items: ArrayList<HashMap<String, String>> = arrayListOf()
                            for (item in it.passenger) {
                                val hashItem = hashMapOf<String, String>()
                                hashItem["id"] = item.studentID
                                hashItem["passengerId"] = item.passengerId.toString()
                                hashItem["name"] = item.name
                                hashItem["profile"] = item.profile
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
            SettingToolbarUtils.showBottomSheetFragment(
                requireActivity(),
                "티켓을 삭제하시겠어요?",
                "패신저에게 고지하셨나요? 이미 입금을 받으셨다면 환불처리를 진행해 주세요.\n" +
                        "패신저가 있는 상태에서 2회 이상 취소시, 추후 서비스를 더이상 이용하실 수 없습니다.\n" +
                        "그래도 삭제하시겠습니까?",
                "티켓삭제",
                onDoSomething = object : CheckDialogFragment.Listener() {
                    override fun onPositiveButtonClick() {
                        reserveDriverViewModel.updateTicketStatus(TicketStatus.Cancel.getTicketStatusDTO())
                        onRewNew()
                    }
                }
            )
        }

        binding.btnConfirm.setOnClickListener {
            SettingToolbarUtils.showBottomSheetFragment(
                requireActivity(),
                "운행을 종료합니다.",
                "유료 운행의 경우 오전7시~9시, 오후 6시~8시 까지 운행을 종료해야 합니다.",
                "운행종료",
                onDoSomething = object : CheckDialogFragment.Listener() {
                    override fun onPositiveButtonClick() {
                        reserveDriverViewModel.updateTicketStatus(TicketStatus.After.getTicketStatusDTO())
                        onRewNew()
                    }
                }
            )
        }

        binding.imageX.setOnClickListener {
            dismissAllowingStateLoss()
        }
        binding.imgTicketRightarrow.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(reserveDriverViewModel.ticketDetail.value.openChatUrl)
            )
            startActivity(intent)
        }
    }
}