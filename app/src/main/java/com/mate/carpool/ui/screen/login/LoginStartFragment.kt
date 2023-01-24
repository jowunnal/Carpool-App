package com.mate.carpool.ui.screen.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.mate.carpool.R
import com.mate.carpool.databinding.FragmentLoginStartBinding
import com.mate.carpool.ui.activity.MainActivity
import com.mate.carpool.ui.base.BindFragment
import com.mate.carpool.ui.screen.register.vm.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginStartFragment  : BindFragment<FragmentLoginStartBinding>(R.layout.fragment_login_start) {
    val registerViewModel: RegisterViewModel by activityViewModels()
    lateinit var mainActivity: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner=viewLifecycleOwner
        binding.btnConfirm.setOnClickListener {
            registerViewModel.loginStudentMember(binding.editStudentNumber.text.toString(),binding.editName.text.toString(),binding.editNumber.text.toString())
        }
        mainActivity = activity as MainActivity

        registerViewModel.loginFlag.observe(viewLifecycleOwner, Observer {
            if(it){
                Navigation.findNavController(view).navigate(R.id.action_loginStartFragment_to_homeFragment)
                registerViewModel.loginFlag.value=false
            }
        })
    }

}