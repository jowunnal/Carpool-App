package com.mate.carpool.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.mate.carpool.ui.theme.MateTheme
import kotlinx.coroutines.launch

abstract class BaseComposeFragment<VM : BaseViewModel> : Fragment() {

    abstract val viewModel: VM
    open val useActionBar: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.run {
            if (useActionBar) show() else hide()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MateTheme {
                    Surface {
                        this@BaseComposeFragment.Content()
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.snackbarMessage.collect { message ->
                    showSnackbar(message = message)
                }
            }
        }
    }

    protected fun showSnackbar(message: String, length: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(requireView(), message, length).show()
    }

    @Composable
    abstract fun Content()
}