package com.mate.carpool.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mate.carpool.BR
import kotlinx.coroutines.launch

abstract class BaseFragment<VM : BaseViewModel, VB : ViewDataBinding> : Fragment() {

    abstract val viewModel: VM
    open val useActionBar: Boolean = true
    protected lateinit var binding: VB

    abstract fun getViewBinding(): VB

    open fun initState() {
        initViews()
        subscribeUi()
    }

    open fun initViews() = Unit

    open fun subscribeUi() = Unit

    override fun onResume() {
        super.onResume()

        (requireActivity() as AppCompatActivity).supportActionBar?.run {
            if (useActionBar) show() else hide()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
        binding.lifecycleOwner = viewLifecycleOwner
        // binding.setVariable(BR.viewModel, viewModel)
        binding.setVariable(BR.navController, findNavController())
        binding.root.setOnTouchListener { v, _ ->
            if (v.hasFocus()) {
                hideKeyboard()
            }
            false
        }

        return binding.root
    }

    private fun hideKeyboard() {
        val manager =
            requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(
            requireActivity().currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
        requireActivity().currentFocus?.clearFocus()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initState()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.snackbarMessage.collect { message ->
                    if (message.contentMessage.isNotBlank()) {
                        showSnackbar(message = message.contentMessage)
                    }
                }
            }
        }
    }

    protected fun showSnackbar(message: String, length: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(binding.root, message, length).show()
    }
}
