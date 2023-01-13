package com.mate.carpool.ui.us.createCarpool.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.mate.carpool.R
import com.mate.carpool.ui.utils.SettingToolbarUtils
import com.mate.carpool.ui.us.createCarpool.vm.CreateTicketViewModel
import com.mate.carpool.databinding.FragmentCreateCarpoolTicketPreviewBinding
import com.mate.carpool.ui.binder.BindFragment

class CreateTicketPreviewFragment : BindFragment<FragmentCreateCarpoolTicketPreviewBinding>(R.layout.fragment_create_carpool_ticket_preview) {
    private val ticketViewModel: CreateTicketViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner=viewLifecycleOwner
        binding.createCarpoolViewModel=ticketViewModel
        binding.navController=Navigation.findNavController(view)

        binding.btnConfirm.setOnClickListener {
            ticketViewModel.createCarpoolTicket()
            Navigation.findNavController(view).navigate(R.id.action_createTicketPreviewFragment_to_homeFragment)
        }

        binding.btnCancel.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        SettingToolbarUtils.setActionBar(requireActivity(), binding.appbarBack)
    }
}