package com.mate.carpool.ui.fragment

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mate.carpool.R
import com.mate.carpool.data.vm.RegisterViewModel
import com.mate.carpool.databinding.FragmentRegisterInfoStep2Binding
import com.mate.carpool.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterInfoStep2Fragment : BaseFragment<RegisterViewModel, FragmentRegisterInfoStep2Binding>() {

    override val viewModel: RegisterViewModel by activityViewModels()

    override fun getViewBinding() = FragmentRegisterInfoStep2Binding.inflate(layoutInflater)

    override fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.studentIdErrorMessage.collect { message ->
                        binding.tvStudentIdErrorMessage.isVisible = message.isNotEmpty()
                        binding.tvStudentIdErrorMessage.text = message
                    }
                }
                launch {
                    viewModel.event.collect { event ->
                        when (event.type) {
                            RegisterViewModel.EVENT_MOVE_TO_NEXT_STEP -> {
                                findNavController().navigate(R.id.action_RegisterInfoStep2Fragment_to_RegisterInfoStep3Fragment)
                            }
                        }
                    }
                }
            }
        }
    }
}