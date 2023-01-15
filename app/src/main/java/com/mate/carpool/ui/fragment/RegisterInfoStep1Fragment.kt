package com.mate.carpool.ui.fragment

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mate.carpool.R
import com.mate.carpool.data.vm.RegisterViewModel
import com.mate.carpool.databinding.FragmentRegisterInfoStep1Binding
import com.mate.carpool.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterInfoStep1Fragment : BaseFragment<RegisterViewModel, FragmentRegisterInfoStep1Binding>() {

    override val viewModel: RegisterViewModel by activityViewModels()

    override fun getViewBinding() = FragmentRegisterInfoStep1Binding.inflate(layoutInflater)
}