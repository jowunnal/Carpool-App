package com.mate.carpool.ui.screen.register.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.mate.carpool.R
import com.mate.carpool.ui.utils.ButtonCheckUtils
import com.mate.carpool.ui.utils.SettingToolbarUtils.setActionBar
import com.mate.carpool.databinding.FragmentRegisterPhoneBinding
import com.mate.carpool.ui.base.BindFragment
import com.mate.carpool.ui.screen.register.vm.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterPhoneFragment : BindFragment<FragmentRegisterPhoneBinding>(R.layout.fragment_register_phone) {
    val registerViewModel: RegisterViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerViewModel = registerViewModel
        binding.navController = Navigation.findNavController(view)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.editNumber.doOnTextChanged { text, start, before, count ->
            binding.btnConfirm.isSelected =
                ButtonCheckUtils.checkRegisterInfoIsCorrect(text.toString(),"[^0-9]",11,11)
        }

        setActionBar(requireActivity(),binding.appbarBack)
    }
}