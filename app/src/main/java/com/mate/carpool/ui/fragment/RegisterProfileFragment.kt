package com.mate.carpool.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.mate.carpool.R
import com.mate.carpool.databinding.FragmentRegisterProfileBinding
import com.mate.carpool.ui.binder.BindFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterProfileFragment : BindFragment<FragmentRegisterProfileBinding>(R.layout.fragment_register_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner=this

        binding.imgProfile.setOnClickListener {

        }
        binding.btnConfirm.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_RegisterProfileFragment_to_RegisterSelectDayFragment)
        }
    }
}