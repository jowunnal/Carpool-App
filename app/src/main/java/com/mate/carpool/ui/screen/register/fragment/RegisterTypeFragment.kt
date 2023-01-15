package com.mate.carpool.ui.screen.register.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.mate.carpool.R
import com.mate.carpool.ui.utils.SettingToolbarUtils
import com.mate.carpool.databinding.FragmentRegisterTypeBinding
import com.mate.carpool.ui.base.BindFragment
import com.mate.carpool.ui.screen.register.vm.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterTypeFragment : BindFragment<FragmentRegisterTypeBinding>(R.layout.fragment_register_type) {
    val registerViewModel: RegisterViewModel by activityViewModels()
    private var btnFlag = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPassenger.isSelected=true
        binding.lifecycleOwner=viewLifecycleOwner
        binding.navController=Navigation.findNavController(view)
        binding.data=this

        binding.btnConfirm.setOnClickListener {
            if(binding.btnDriver.isSelected){
                registerViewModel.mutableUserModel.value?.studentType="DRIVER"
            }
            else{
                registerViewModel.mutableUserModel.value?.studentType="PASSENGER"
            }
            Navigation.findNavController(view).navigate(R.id.action_RegisterTypeFragment_to_RegisterProfileFragment)
        }

        SettingToolbarUtils.setActionBar(requireActivity(), binding.appbarBack)

    }

    fun transDriverPassenger(){
        if(!btnFlag){
            binding.btnPassenger.startAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.anim_button_driver_to_passenger_on))
            binding.btnPassenger.isSelected=true
            binding.btnPassenger.setTextColor(Color.WHITE)

            CoroutineScope(Dispatchers.Main).launch {
                delay(120)
                binding.btnDriver.isSelected=false
                binding.btnDriver.setTextColor(requireActivity().resources.getColor(R.color.blue))
                btnFlag=true
            }
        }
        else{
            binding.btnDriver.startAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.anim_button_driver_to_passenger_off))
            binding.btnDriver.isSelected=true
            binding.btnDriver.setTextColor(requireActivity().resources.getColor(R.color.white))

            CoroutineScope(Dispatchers.Main).launch {
                delay(120)
                binding.btnPassenger.isSelected=false
                binding.btnPassenger.setTextColor(requireActivity().resources.getColor(R.color.blue))
                btnFlag=false
            }
        }
    }

}