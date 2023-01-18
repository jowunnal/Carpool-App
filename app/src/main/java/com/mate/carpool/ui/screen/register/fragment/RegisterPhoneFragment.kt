package com.mate.carpool.ui.screen.register.fragment

import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mate.carpool.R
import com.mate.carpool.ui.utils.ButtonCheckUtils
import com.mate.carpool.ui.utils.SettingToolbarUtils.setActionBar
import com.mate.carpool.databinding.FragmentRegisterPhoneBinding
import com.mate.carpool.ui.base.BaseFragment
import com.mate.carpool.ui.screen.register.vm.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.update
import java.lang.Exception
import java.util.Locale

@AndroidEntryPoint
class RegisterPhoneFragment : BaseFragment<RegisterViewModel,FragmentRegisterPhoneBinding>() {

    override val viewModel: RegisterViewModel by activityViewModels()

    override fun getViewBinding() = FragmentRegisterPhoneBinding.inflate(layoutInflater)

    override fun initViews() {
        binding.etPhone.addTextChangedListener(object : PhoneNumberFormattingTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
                viewModel.phone.update { s?.toString() ?: "" }
            }
        })
    }

    // TODO 뒤로가기 버튼 누르면 '지금까지 입력한 정보가 모두 사라집니다' 이런 얼럿 있으면 좋을 것 같음 -> 기획 문의
}