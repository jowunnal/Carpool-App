package com.mate.carpool.ui.screen.login

import android.graphics.Paint
import androidx.fragment.app.viewModels
import com.mate.carpool.databinding.FragmentLoginBinding
import com.mate.carpool.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>(){

    override val viewModel: LoginViewModel by viewModels()

    override fun getViewBinding(): FragmentLoginBinding = FragmentLoginBinding.inflate(layoutInflater)

    override fun initViews() = with(binding) {
        tvLogin.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }
}