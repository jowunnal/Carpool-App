package com.mate.carpool.ui.fragment.us5

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.mate.carpool.R
import com.mate.carpool.data.utils.SettingToolbarUtils
import com.mate.carpool.data.vm.CreateTicketViewModel
import com.mate.carpool.databinding.FragmentCreateCarpoolTicketBoardingAreaBinding
import com.mate.carpool.ui.binder.BindFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTicketBoardingAreaFragment : BindFragment<FragmentCreateCarpoolTicketBoardingAreaBinding>(R.layout.fragment_create_carpool_ticket_boarding_area) {
    private val ticketViewModel:CreateTicketViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.context=requireActivity()
        binding.lifecycleOwner=viewLifecycleOwner
        binding.createCarpoolViewModel = ticketViewModel
        binding.navController=Navigation.findNavController(view)
        binding.startingAreaList= arrayListOf("인동","옥계","대구","그 외","경운대학교")

        ticketViewModel.boardingAreaButtonFlag.observe(viewLifecycleOwner, Observer {
            binding.btnConfirm.isSelected = it[0]&&it[1]
        })

        SettingToolbarUtils.setActionBar(requireActivity(), binding.appbarBack)
    }
}