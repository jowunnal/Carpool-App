package com.mate.carpool.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.internal.ViewUtils.hideKeyboard

abstract class BaseFragment<VM : BaseViewModel, VB : ViewDataBinding> : Fragment() {

    abstract val viewModel: VM

    protected lateinit var binding: VB

    abstract fun getViewBinding(): VB

    open fun initState() {
        initViews()
        subscribeUI()
    }

    open fun initViews() = Unit

    abstract fun subscribeUI()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.root.setOnTouchListener { v, _ ->
            if (v.hasFocus()) {
                hideKeyboard()
            }
            false
        }

        return binding.root
    }

    private fun hideKeyboard() {
        val manager = requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        requireActivity().currentFocus?.clearFocus()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initState()
    }
}
