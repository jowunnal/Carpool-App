package com.mate.carpool.ui.screen.createCarpool.fragment

import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.mate.carpool.R
import com.mate.carpool.databinding.FragmentCreateCarpoolTicketPreviewBinding
import com.mate.carpool.ui.base.BaseFragment
import com.mate.carpool.ui.base.Event
import com.mate.carpool.ui.screen.createCarpool.vm.CreateTicketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTicketPreviewFragment : BaseFragment<CreateTicketViewModel,FragmentCreateCarpoolTicketPreviewBinding>() {

    override val viewModel: CreateTicketViewModel by hiltNavGraphViewModels(R.id.createTicket)

    override fun getViewBinding(): FragmentCreateCarpoolTicketPreviewBinding = FragmentCreateCarpoolTicketPreviewBinding.inflate(layoutInflater)

    override val useActionBar: Boolean = true

    override fun initViews() = with(binding) {
        createTicketViewModel = viewModel
        context = requireActivity()

        btnConfirm.setOnClickListener {
            viewModel.fetch()
            val action = CreateTicketPreviewFragmentDirections.actionCreateTicketPreviewFragmentToHomeFragment(CreateTicketViewModel.EVENT_CREATED_TICKET)
            findNavController().navigate(action)
        }

        btnCancel.setOnClickListener {
            val action = CreateTicketPreviewFragmentDirections.actionCreateTicketPreviewFragmentToHomeFragment(Event.EVENT_FINISH)
            findNavController().navigate(action)
        }
    }
}