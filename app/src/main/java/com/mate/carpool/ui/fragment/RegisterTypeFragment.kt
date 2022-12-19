package com.mate.carpool.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mate.carpool.R
import com.mate.carpool.databinding.FragmentRegisterTypeBinding
import com.mate.carpool.ui.binder.BindFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterTypeFragment : BindFragment<FragmentRegisterTypeBinding>(R.layout.fragment_register_type) {
    private var btnFlag = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*val btnNext = view.findViewById<Button>(R.id.btn_confirm)
        btnNext.setOnClickListener {
            findNavController().navigate(RegisterTypeFragmentDirections.actionRegisterTypeFragmentToRegisterProfileFragment())
        }*/
        binding.button2.isSelected=true
        binding.lifecycleOwner=this
        binding.data=this

    }

    fun transDriverPassenger(){
        if(!btnFlag){
            binding.button2.startAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.anim_button_driver_to_passenger_on))
            binding.button2.isSelected=true
            binding.button2.setTextColor(Color.WHITE)

            CoroutineScope(Dispatchers.Main).launch {
                delay(120)
                binding.button.isSelected=false
                binding.button.setTextColor(requireActivity().resources.getColor(R.color.blue))
                btnFlag=true
            }
        }
        else{
            binding.button.startAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.anim_button_driver_to_passenger_off))
            binding.button.isSelected=true
            binding.button.setTextColor(requireActivity().resources.getColor(R.color.white))

            CoroutineScope(Dispatchers.Main).launch {
                delay(120)
                binding.button2.isSelected=false
                binding.button2.setTextColor(requireActivity().resources.getColor(R.color.blue))
                btnFlag=false
            }
        }
    }
}