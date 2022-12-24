package com.mate.carpool.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.mate.carpool.R
import com.mate.carpool.data.vm.RegisterViewModel
import com.mate.carpool.databinding.FragmentLoginStartBinding
import com.mate.carpool.ui.binder.BindFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginStartFragment  : BindFragment<FragmentLoginStartBinding>(R.layout.fragment_login_start) {
    val registerViewModel: RegisterViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner=this
        binding.btnConfirm.setOnClickListener {
            registerViewModel.loginStudentMember(binding.editStudentNumber.text.toString(),binding.editName.text.toString(),binding.editNumber.text.toString())
        }

    }

}