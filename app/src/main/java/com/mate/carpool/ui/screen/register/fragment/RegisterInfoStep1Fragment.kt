package com.mate.carpool.ui.fragment

import androidx.fragment.app.activityViewModels
import com.mate.carpool.databinding.FragmentRegisterInfoStep1Binding
import com.mate.carpool.ui.base.BaseFragment
import com.mate.carpool.ui.screen.register.vm.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterInfoStep1Fragment : BaseFragment<RegisterViewModel,FragmentRegisterInfoStep1Binding>() {

    override val viewModel: RegisterViewModel by activityViewModels()

    override fun getViewBinding() = FragmentRegisterInfoStep1Binding.inflate(layoutInflater)
}