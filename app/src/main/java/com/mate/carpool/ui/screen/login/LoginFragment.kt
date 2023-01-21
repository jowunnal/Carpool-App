package com.mate.carpool.ui.screen.login

import android.graphics.Paint
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.mate.carpool.data.vm.LoginViewModel
import com.mate.carpool.databinding.FragmentLoginBinding
import com.mate.carpool.ui.base.BaseFragment
import com.mate.carpool.ui.screen.register.vm.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<RegisterViewModel, FragmentLoginBinding>(){

    override val viewModel: RegisterViewModel by activityViewModels()
    override fun getViewBinding(): FragmentLoginBinding = FragmentLoginBinding.inflate(layoutInflater)
    override fun initViews() = with(binding) {
        navController = findNavController()
        tvLogin.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    override fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.toastMessage.collectLatest {
                    if(it!="") {
                        Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}