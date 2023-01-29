package com.mate.carpool.ui.screen.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.mate.carpool.R
import com.mate.carpool.ui.base.BaseComposeFragment
import com.mate.carpool.ui.composable.rememberLambda
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseComposeFragment<SignUpViewModel>() {

    override val viewModel: SignUpViewModel by viewModels()

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    override fun Content() {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        SignUpScreen(
            uiState = uiState,
            onNameEdit = viewModel::setName,
            onEmailEdit = viewModel::setEmail,
            onPasswordEdit = viewModel::setPassword,
            onShowPasswordClick = rememberLambda {
                if (uiState.showPassword) {
                    viewModel.setShowPassword(false)
                } else {
                    viewModel.setShowPassword(true)
                }
            },
            onSignUpClick = viewModel::signUp,
            onBackClick = rememberLambda {
                findNavController().popBackStack()
            },
            moveToHomeScreen = rememberLambda {
                findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
            }
        )
    }
}