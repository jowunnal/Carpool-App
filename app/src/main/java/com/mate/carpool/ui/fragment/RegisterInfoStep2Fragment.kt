package com.mate.carpool.ui.fragment

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mate.carpool.data.vm.RegisterViewModel
import com.mate.carpool.databinding.FragmentRegisterInfoStep2Binding
import com.mate.carpool.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterInfoStep2Fragment : BaseFragment<RegisterViewModel, FragmentRegisterInfoStep2Binding>() {

    override val viewModel: RegisterViewModel by activityViewModels()

    override fun getViewBinding() = FragmentRegisterInfoStep2Binding.inflate(layoutInflater)

    override fun subscribeUI() {

    }

    override fun initViews() = with(binding) {
        viewModel = this@RegisterInfoStep2Fragment.viewModel
        navController = findNavController()
    }
}