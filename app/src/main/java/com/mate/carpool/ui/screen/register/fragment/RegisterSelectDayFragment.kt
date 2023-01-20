package com.mate.carpool.ui.screen.register.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mate.carpool.R
import com.mate.carpool.data.model.domain.item.WeekItem
import com.mate.carpool.databinding.FragmentRegisterSelectDayBinding
import com.mate.carpool.ui.base.BindFragment
import com.mate.carpool.ui.screen.register.vm.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterSelectDayFragment : BindFragment<FragmentRegisterSelectDayBinding>(R.layout.fragment_register_select_day) {

    val registerViewModel: RegisterViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner=viewLifecycleOwner
        binding.navController = Navigation.findNavController(view)

        binding.btnConfirm.setOnClickListener {
            val mutableList = arrayListOf<String>()
            registerViewModel.mutableUserModel.value!!.studentDayCodes = mutableList.toList()
            Navigation.findNavController(view).navigate(R.id.action_RegisterSelectDayFragment_to_loginFragment)
            registerViewModel.signUpStudentMember()
        }

    }
}