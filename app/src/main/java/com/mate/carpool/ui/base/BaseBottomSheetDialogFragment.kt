package com.mate.carpool.ui.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mate.carpool.R
import com.mate.carpool.ui.utils.LayoutParamsUtils

abstract class BaseBottomSheetDialogFragment<T:ViewDataBinding>(@LayoutRes private val layoutRes: Int): BottomSheetDialogFragment()  {
    private var _binding :T ?= null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutRes,container,false)
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun onDestroy() {
        _binding=null
        super.onDestroy()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme).apply {
            behavior.peekHeight = LayoutParamsUtils.getBottomSheetDialogDefaultHeight(93, requireActivity())
        }
        return dialog
    }

    abstract class Renewing{
        abstract fun onRewNew()
    }
}