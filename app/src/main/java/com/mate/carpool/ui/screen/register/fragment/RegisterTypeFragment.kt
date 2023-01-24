package com.mate.carpool.ui.screen.register.fragment

import android.graphics.Color
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mate.carpool.R
import com.mate.carpool.databinding.FragmentRegisterTypeBinding
import com.mate.carpool.ui.base.BaseFragment
import com.mate.carpool.ui.screen.register.vm.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterTypeFragment : BaseFragment<RegisterViewModel,FragmentRegisterTypeBinding>() {

    override val viewModel : RegisterViewModel by activityViewModels()

    override fun getViewBinding(): FragmentRegisterTypeBinding = FragmentRegisterTypeBinding.inflate(layoutInflater)

    override fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.type.collectLatest {
                    transDriverPassenger(it)
                }
            }
        }
    }

    override fun initViews() = with(binding){
        btnPassenger.setOnClickListener {
            this@RegisterTypeFragment.viewModel.type.value=!this@RegisterTypeFragment.viewModel.type.value
        }
        btnDriver.setOnClickListener {
            this@RegisterTypeFragment.viewModel.type.value=!this@RegisterTypeFragment.viewModel.type.value
        }
    }


    private fun transDriverPassenger(typeState:Boolean){
        if(!typeState){
            binding.btnPassenger.startAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.anim_button_driver_to_passenger_on))
            binding.btnPassenger.setTextColor(Color.WHITE)

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                delay(120)
                binding.btnDriver.setTextColor(requireActivity().resources.getColor(R.color.blue))
            }
        }
        else{
            binding.btnDriver.startAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.anim_button_driver_to_passenger_off))
            binding.btnDriver.setTextColor(requireActivity().resources.getColor(R.color.white))

            viewLifecycleOwner.lifecycleScope.launchWhenStarted  {
                delay(120)
                binding.btnPassenger.setTextColor(requireActivity().resources.getColor(R.color.blue))
            }
        }
    }

}