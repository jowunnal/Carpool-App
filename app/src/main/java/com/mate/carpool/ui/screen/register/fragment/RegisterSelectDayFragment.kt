package com.mate.carpool.ui.screen.register.fragment

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mate.carpool.R
import com.mate.carpool.databinding.FragmentRegisterSelectDayBinding
import com.mate.carpool.ui.base.BaseFragment
import com.mate.carpool.ui.screen.register.vm.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterSelectDayFragment : BaseFragment<RegisterViewModel,FragmentRegisterSelectDayBinding>(){

    override val viewModel: RegisterViewModel by activityViewModels()

    override fun getViewBinding(): FragmentRegisterSelectDayBinding = FragmentRegisterSelectDayBinding.inflate(layoutInflater)

    override fun initViews() = with(binding){
        btnConfirm.setOnClickListener {
            findNavController().navigate(R.id.action_RegisterSelectDayFragment_to_loginFragment)
            this@RegisterSelectDayFragment.viewModel.signUpStudentMember()
        }
    }
}