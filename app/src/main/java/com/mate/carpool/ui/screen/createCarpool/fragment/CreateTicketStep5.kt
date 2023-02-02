package com.mate.carpool.ui.screen.createCarpool.fragment

import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.mate.carpool.R
import com.mate.carpool.databinding.FragmentCreateCarpoolTicketStep5Binding
import com.mate.carpool.ui.base.BaseFragment
import com.mate.carpool.ui.screen.createCarpool.vm.CreateTicketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTicketStep5: BaseFragment<CreateTicketViewModel,FragmentCreateCarpoolTicketStep5Binding>() {

    override val viewModel: CreateTicketViewModel by hiltNavGraphViewModels(R.id.createTicket)

    override fun getViewBinding(): FragmentCreateCarpoolTicketStep5Binding = FragmentCreateCarpoolTicketStep5Binding.inflate(layoutInflater)

    override val useActionBar: Boolean = true

    override fun initViews() = with(binding) {
        createTicketViewModel = viewModel
        context = requireActivity()
        recruitNumberList = listOf("0","1","2","3")
    }
}