package com.mate.carpool.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.mate.carpool.R
import com.mate.carpool.data.vm.RegisterViewModel
import com.mate.carpool.databinding.FragmentLoginStartBinding
import com.mate.carpool.ui.activity.MainActivity
import com.mate.carpool.ui.binder.BindFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginStartFragment  : BindFragment<FragmentLoginStartBinding>(R.layout.fragment_login_start) {
    val registerViewModel: RegisterViewModel by activityViewModels()
    lateinit var mainActivity:MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner=viewLifecycleOwner
        binding.btnConfirm.setOnClickListener {
            registerViewModel.loginStudentMember(binding.editStudentNumber.text.toString(),binding.editName.text.toString(),binding.editNumber.text.toString())
        }
        mainActivity = activity as MainActivity

        mainActivity.setOnBackPressedListener(object : MainActivity.OnBackPressedListener{
            override fun onBack() {
                mainActivity.setOnBackPressedListener(null)
                mainActivity.onBackPressed()
            }
        })

        registerViewModel.loginFlag.observe(viewLifecycleOwner, Observer {
            if(it){
                Navigation.findNavController(view).navigate(R.id.action_loginStartFragment_to_fragmentRegisterCarpool)
                registerViewModel.loginFlag.value=false
            }
        })
    }

}