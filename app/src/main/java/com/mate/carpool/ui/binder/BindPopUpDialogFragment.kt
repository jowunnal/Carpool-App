package com.mate.carpool.ui.binder

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.mate.carpool.ui.utils.LayoutParamsUtils

abstract class BindPopUpDialogFragment<VB: ViewBinding>(private val location:IntArray,@LayoutRes private val layoutRes:Int) : DialogFragment() {
    private var _binding : VB ?= null
    val binding get() = _binding!!

    abstract fun bindFragment(inflater:LayoutInflater,container:ViewGroup?) : VB

    open fun initState() {
        initView()
        subScribeUi()
    }

    abstract fun initView()

    abstract fun subScribeUi()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindFragment(inflater,container)

        dialog?.window?.apply {
            requestFeature(Window.FEATURE_NO_TITLE)
            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            setGravity(Gravity.TOP or Gravity.START)
            attributes!!.apply {
                x = location[0] - LayoutParamsUtils.getBottomSheetDialogDefaultWidth(18, requireActivity())
                y = location[1]
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initState()
    }
}

