package com.mate.carpool.ui.screen.createCarpool.fragment

import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.mate.carpool.R
import com.mate.carpool.data.model.domain.StartArea
import com.mate.carpool.databinding.FragmentCreateCarpoolTicketStep1Binding
import com.mate.carpool.ui.base.BaseFragment
import com.mate.carpool.ui.screen.createCarpool.vm.CreateTicketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTicketStep1 :
    BaseFragment<CreateTicketViewModel, FragmentCreateCarpoolTicketStep1Binding>() {

    override val viewModel: CreateTicketViewModel by hiltNavGraphViewModels(R.id.createTicket)

    override fun getViewBinding(): FragmentCreateCarpoolTicketStep1Binding =
        FragmentCreateCarpoolTicketStep1Binding.inflate(layoutInflater)

    override val useActionBar: Boolean = true

    override fun initViews() = with(binding) {
        context = requireActivity()
        createTicketViewModel = viewModel
        startingAreaList = StartArea.values().map { it.displayName }
    }
}