package com.mate.carpool.ui.fragment

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mate.carpool.R
import com.mate.carpool.data.vm.RegisterViewModel
import com.mate.carpool.databinding.FragmentRegisterInfoStep3Binding
import com.mate.carpool.ui.base.CommonDialogFragment
import com.mate.carpool.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterInfoStep3Fragment : BaseFragment<RegisterViewModel, FragmentRegisterInfoStep3Binding>() {

    override val viewModel: RegisterViewModel by activityViewModels()

    override fun getViewBinding() = FragmentRegisterInfoStep3Binding.inflate(layoutInflater)

    override fun initViews() = with(binding) {
        btnConfirm.setOnClickListener { openConfirmInputDialog() }
    }

    private fun openConfirmInputDialog() {
        CommonDialogFragment.show(
            fragmentManager = childFragmentManager,
            title = "입력하신 정보가 정확하신가요?",
            message = "이름, 학번과 학과는 추후 수정이 불가능합니다. 다음으로 넘어가시겠어요?",
            listener = object : CommonDialogFragment.Listener() {
                override fun onPositiveButtonClick() {
                    val navController = findNavController()
                    navController.navigate(R.id.action_RegisterInfoStep3Fragment_to_RegisterPhoneFragment)
                }
            }
        )
    }
}