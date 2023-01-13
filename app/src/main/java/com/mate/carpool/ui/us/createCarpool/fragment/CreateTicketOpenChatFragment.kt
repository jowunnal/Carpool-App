package com.mate.carpool.ui.us.createCarpool.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.mate.carpool.R
import com.mate.carpool.ui.utils.SettingToolbarUtils
import com.mate.carpool.ui.us.createCarpool.vm.CreateTicketViewModel
import com.mate.carpool.databinding.FragmentCreateCarpoolTicketOpenChatBinding
import com.mate.carpool.ui.binder.BindFragment

class CreateTicketOpenChatFragment : BindFragment<FragmentCreateCarpoolTicketOpenChatBinding>(R.layout.fragment_create_carpool_ticket_open_chat) {
    private val ticketViewModel: CreateTicketViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.context=requireActivity()
        binding.lifecycleOwner=viewLifecycleOwner
        binding.createCarpoolViewModel = ticketViewModel
        binding.navController= Navigation.findNavController(view)
        binding.boardingNumber= arrayListOf("1","2","3","4")
        binding.boardingFee= arrayListOf("무료","유료")

        ticketViewModel.openChatButtonFlag.observe(viewLifecycleOwner, Observer {
            binding.btnConfirm.isSelected = it[0]&&it[1]&&it[2]
        })

        SettingToolbarUtils.setActionBar(requireActivity(), binding.appbarBack)
    }
}