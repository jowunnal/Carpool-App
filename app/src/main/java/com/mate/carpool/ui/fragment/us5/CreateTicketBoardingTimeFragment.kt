package com.mate.carpool.ui.fragment.us5

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.mate.carpool.R
import com.mate.carpool.data.utils.SettingToolbarUtils
import com.mate.carpool.data.vm.CreateTicketViewModel
import com.mate.carpool.databinding.FragmentCreateCarpoolTicketBoardingTimeBinding
import com.mate.carpool.ui.binder.BindFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTicketBoardingTimeFragment :BindFragment<FragmentCreateCarpoolTicketBoardingTimeBinding>(R.layout.fragment_create_carpool_ticket_boarding_time){
    private val ticketViewModel: CreateTicketViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.context=requireActivity()
        binding.lifecycleOwner=viewLifecycleOwner
        binding.createCarpoolViewModel = ticketViewModel
        binding.navController= Navigation.findNavController(view)
        binding.dayStatus= arrayListOf("오전","오후")

        ticketViewModel.boardingTimeButtonFlag.observe(viewLifecycleOwner, Observer {
            binding.btnConfirm.isSelected = it[0]&&it[1]&&it[2]
        })

        SettingToolbarUtils.setActionBar(requireActivity(), binding.appbarBack)
    }
}