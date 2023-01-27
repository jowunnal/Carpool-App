package com.mate.carpool.ui.screen.onboarding

import android.graphics.Paint
import androidx.fragment.app.viewModels
import com.mate.carpool.databinding.FragmentOnboardingBinding
import com.mate.carpool.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment<OnBoardingViewModel, FragmentOnboardingBinding>() {

    override val viewModel: OnBoardingViewModel by viewModels()

    override fun getViewBinding() = FragmentOnboardingBinding.inflate(layoutInflater)

    override fun initViews() = with(binding) {
        tvLogin.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }
}