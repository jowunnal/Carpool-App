package com.mate.carpool.ui.screen.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.mate.carpool.R
import com.mate.carpool.ui.base.BaseComposeFragment
import com.mate.carpool.ui.base.SnackBarMessage
import com.mate.carpool.ui.composable.rememberLambda
import com.mate.carpool.ui.navigation.NavigationFragmentDirections
import com.mate.carpool.ui.screen.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseComposeFragment<LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    override fun Content() {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val snackBarMessage by viewModel.snackbarMessage.collectAsStateWithLifecycle(
            initialValue = SnackBarMessage.getInitValues(),
            lifecycleOwner = LocalLifecycleOwner.current
        )

        LoginScreen(
            uiState = uiState,
            snackBarMessage = snackBarMessage,
            onEmailEdit = viewModel::setEmail,
            onPasswordEdit = viewModel::setPassword,
            onShowPasswordClick = rememberLambda {
                if (uiState.showPassword) {
                    viewModel.setShowPassword(false)
                } else {
                    viewModel.setShowPassword(true)
                }
            },
            onLoginClick = viewModel::login,
            onBackClick = rememberLambda {
                findNavController().popBackStack()
            },
            moveToHomeScreen = rememberLambda {
                val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment(SplashViewModel.EVENT_GO_TO_HOME_SCREEN)
                findNavController().navigate(action)
            }
        )
    }
}