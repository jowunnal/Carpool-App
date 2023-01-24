package com.mate.carpool.ui.screen.register.fragment

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mate.carpool.R
import com.mate.carpool.databinding.FragmentRegisterSelectDayBinding
import com.mate.carpool.ui.base.BaseFragment
import com.mate.carpool.ui.screen.register.vm.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterSelectDayFragment : BaseFragment<RegisterViewModel,FragmentRegisterSelectDayBinding>(){

    override val viewModel: RegisterViewModel by activityViewModels()

    override fun getViewBinding(): FragmentRegisterSelectDayBinding = FragmentRegisterSelectDayBinding.inflate(layoutInflater)

    override fun initViews() = with(binding){
        btnConfirm.setOnClickListener {
            findNavController().navigate(R.id.action_RegisterSelectDayFragment_to_loginFragment)
            viewModel.signUpStudentMember()
        }
    }
}