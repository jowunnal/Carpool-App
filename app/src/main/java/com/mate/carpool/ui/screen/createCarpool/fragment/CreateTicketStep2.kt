package com.mate.carpool.ui.screen.createCarpool.fragment

import android.annotation.SuppressLint
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.mate.carpool.R
import com.mate.carpool.databinding.FragmentCreateCarpoolTicketStep2Binding
import com.mate.carpool.ui.base.BaseFragment
import com.mate.carpool.ui.screen.createCarpool.vm.CreateTicketViewModel
import com.mate.carpool.ui.util.date
import com.mate.carpool.ui.util.month
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CreateTicketStep2 : BaseFragment<CreateTicketViewModel,FragmentCreateCarpoolTicketStep2Binding>(){

    override val viewModel: CreateTicketViewModel by hiltNavGraphViewModels(R.id.createTicket)

    override fun getViewBinding(): FragmentCreateCarpoolTicketStep2Binding = FragmentCreateCarpoolTicketStep2Binding.inflate(layoutInflater)

    override val useActionBar: Boolean = true

    @SuppressLint("SetTextI18n")
    override fun initViews() = with(binding) {
        createTicketViewModel = viewModel

        val tomorrow = Calendar.getInstance().apply {
            time = Date()
            add(Calendar.DATE,1)
        }
        tvStartingDate.text = "내일 ${tomorrow.month}월 ${tomorrow.date}일"

        tlBoardingTime.setOnClickListener {
            TimePickerCustomDialog(viewModel::setStartTime).show(requireActivity().supportFragmentManager,"TimePicker")
        }
    }
}