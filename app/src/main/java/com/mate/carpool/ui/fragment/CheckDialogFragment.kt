package com.mate.carpool.ui.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.mate.carpool.R
import com.mate.carpool.databinding.DialogCheckBinding

class CheckDialogFragment :DialogFragment() {
    private var _binding:DialogCheckBinding ?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=DialogCheckBinding.inflate(inflater,container,false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.title.text="입력하신 정보가 정확한가요?"
        binding.message.text="이름, 학번과 학과는 추후 수정이 불가능합니다.\n다음으로 넘어가시겠어요?"

        binding.positiveButton.setOnClickListener {
            dismiss()
            NavHostFragment.findNavController(this).navigate(R.id.action_RegisterInfoFragment_to_RegisterPhoneFragment)
        }
        binding.negativeButton.setOnClickListener {
            dismiss()
        }
    }
}