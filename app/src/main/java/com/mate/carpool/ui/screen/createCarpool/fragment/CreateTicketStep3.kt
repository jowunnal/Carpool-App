package com.mate.carpool.ui.screen.createCarpool.fragment

import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.mate.carpool.R
import com.mate.carpool.databinding.FragmentCreateCarpoolTicketStep3Binding
import com.mate.carpool.ui.base.BaseFragment
import com.mate.carpool.ui.screen.createCarpool.vm.CreateTicketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTicketStep3 : BaseFragment<CreateTicketViewModel,FragmentCreateCarpoolTicketStep3Binding>() {

    override val viewModel: CreateTicketViewModel by hiltNavGraphViewModels(R.id.createTicket)

    override fun getViewBinding(): FragmentCreateCarpoolTicketStep3Binding = FragmentCreateCarpoolTicketStep3Binding.inflate(layoutInflater)

    override val useActionBar: Boolean = true

    override fun initViews() = with(binding) {
        createTicketViewModel = viewModel
    }
}