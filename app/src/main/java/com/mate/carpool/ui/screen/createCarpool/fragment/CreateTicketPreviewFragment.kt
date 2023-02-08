package com.mate.carpool.ui.screen.createCarpool.fragment

import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mate.carpool.R
import com.mate.carpool.databinding.FragmentCreateCarpoolTicketPreviewBinding
import com.mate.carpool.ui.base.BaseFragment
import com.mate.carpool.ui.base.CommonDialogFragment
import com.mate.carpool.ui.base.Event
import com.mate.carpool.ui.screen.createCarpool.item.CreateTicketUiState
import com.mate.carpool.ui.screen.createCarpool.vm.CreateTicketViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateTicketPreviewFragment : BaseFragment<CreateTicketViewModel,FragmentCreateCarpoolTicketPreviewBinding>() {

    override val viewModel: CreateTicketViewModel by hiltNavGraphViewModels(R.id.createTicket)

    override fun getViewBinding(): FragmentCreateCarpoolTicketPreviewBinding = FragmentCreateCarpoolTicketPreviewBinding.inflate(layoutInflater)

    override val useActionBar: Boolean = true

    private var uiState: CreateTicketUiState ?= null

    override fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    uiState = it
                }
            }
        }
    }

    override fun initViews() = with(binding) {
        createTicketViewModel = viewModel
        context = requireActivity()

        btnConfirm.setOnClickListener {
            uiState?.let {
                viewModel.fetch(
                    startArea = uiState!!.startArea,
                    startTime = uiState!!.startTime,
                    endArea = uiState!!.endArea,
                    boardingPlace = uiState!!.boardingPlace,
                    openChatUrl = uiState!!.openChatLink,
                    recruitPerson = uiState!!.recruitNumber,
                    fee = uiState!!.fee
                )
                val action = CreateTicketPreviewFragmentDirections.actionCreateTicketPreviewFragmentToHomeFragment(CreateTicketViewModel.EVENT_CREATED_TICKET)
                findNavController().navigate(action)
            }
        }

        btnCancel.setOnClickListener {
            CommonDialogFragment.show(
                fragmentManager = requireActivity().supportFragmentManager,
                title = "티켓 생성을 취소하시겠어요?",
                message = "작성중이던 내용은 저장되지 않아요! 그래도 계속하시겠어요?",
                positiveButtonText = "네. 취소할래요.",
                negativeButtonText = "아뇨, 유지할게요.",
                listener = object : CommonDialogFragment.Listener() {
                    override fun onPositiveButtonClick() {
                        val action = CreateTicketPreviewFragmentDirections.actionCreateTicketPreviewFragmentToHomeFragment(Event.EVENT_FINISH)
                        findNavController().navigate(action)
                    }
                }
            )
        }
    }
}