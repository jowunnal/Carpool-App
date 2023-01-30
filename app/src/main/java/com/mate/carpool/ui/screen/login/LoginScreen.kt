package com.mate.carpool.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mate.carpool.R
import com.mate.carpool.ui.base.SnackBarMessage
import com.mate.carpool.ui.composable.LargeDefaultTextField
import com.mate.carpool.ui.composable.SnackBarHostCustom
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.composable.button.LargePrimaryButton
import com.mate.carpool.ui.composable.layout.CommonLayout
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.util.tu

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    snackBarMessage: SnackBarMessage,
    onEmailEdit: (String) -> Unit,
    onPasswordEdit: (String) -> Unit,
    onShowPasswordClick: () -> Unit,
    onLoginClick: () -> Unit,
    onBackClick: () -> Unit,
    moveToHomeScreen: () -> Unit,
) {
    val passwordTextFieldFocusRequest = remember { FocusRequester() }

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    if(snackBarMessage.contentMessage.isNotBlank())
        LaunchedEffect(key1 = snackBarMessage.contentMessage){
            snackBarHostState.showSnackbar(
                message = snackBarMessage.contentMessage,
                duration = SnackbarDuration.Indefinite,
                actionLabel = snackBarMessage.headerMessage
            )
        }

    CommonLayout(
        title = null,
        onBackClick = onBackClick,
        snackBarHost = {
            SnackBarHostCustom(
                headerMessage = it.currentSnackbarData?.message ?: "",
                contentMessage = it.currentSnackbarData?.actionLabel ?: "",
                snackBarHostState = snackBarHostState,
                disMissSnackBar = { snackBarHostState.currentSnackbarData?.dismiss() }
            )
        }
    ) {
        Text(
            text = "로그인하기",
            color = black,
            fontWeight = FontWeight.Bold,
            fontSize = 22.tu
        )
        VerticalSpacer(height = 30.dp)
        LargeDefaultTextField(
            label = "이메일",
            value = uiState.email,
            onValueChange = onEmailEdit,
            placeholder = "이메일을 입력해주세요.",
            errorMessage = if (uiState.invalidEmail) "이메일을 다시 입력해주세요." else null,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions {
                passwordTextFieldFocusRequest.requestFocus()
            }
        )
        VerticalSpacer(height = 24.dp)
        LargeDefaultTextField(
            modifier = Modifier.focusRequester(passwordTextFieldFocusRequest),
            label = "비밀번호",
            value = uiState.password,
            onValueChange = onPasswordEdit,
            placeholder = "비밀번호를 입력해주세요.",
            errorMessage = if (uiState.invalidPassword) "비밀번호를 다시 입력해주세요." else null,
            tailIcon = {
                Image(
                    modifier = Modifier.clickable(onClick = onShowPasswordClick),
                    painter = if (uiState.showPassword) {
                        painterResource(id = R.drawable.ic_visibility_24)
                    } else {
                        painterResource(id = R.drawable.ic_visibility_off_24)
                    },
                    contentDescription = "show password or hide password"
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = if (uiState.showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
        )
        Spacer(modifier = Modifier.weight(1f))
        LargePrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = "로그인하기",
            onClick = onLoginClick,
            enabled = uiState.enableLogin,
        )
    }

    LaunchedEffect(key1 = uiState.loginSuccess) {
        if (uiState.loginSuccess) {
            moveToHomeScreen()
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview1() {
    val uiState = LoginUiState.getInitialValue()
    LoginScreen(
        uiState = uiState,
        snackBarMessage = SnackBarMessage.getInitValues(),
        onEmailEdit = {},
        onPasswordEdit = {},
        onShowPasswordClick = {},
        onLoginClick = {},
        onBackClick = {},
        moveToHomeScreen = {},
    )
}

@Preview
@Composable
private fun LoginScreenPreview2() {
    val uiState = LoginUiState.getInitialValue().copy(password = "password")
    LoginScreen(
        uiState = uiState,
        snackBarMessage = SnackBarMessage.getInitValues(),
        onEmailEdit = {},
        onPasswordEdit = {},
        onShowPasswordClick = {},
        onLoginClick = {},
        onBackClick = {},
        moveToHomeScreen = {},
    )
}

@Preview
@Composable
private fun LoginScreenPreview3() {
    val uiState = LoginUiState.getInitialValue().copy(
        email = "kanggumsil@mate.com", password = "password", showPassword = true
    )
    LoginScreen(
        uiState = uiState,
        snackBarMessage = SnackBarMessage.getInitValues(),
        onEmailEdit = {},
        onPasswordEdit = {},
        onShowPasswordClick = {},
        onLoginClick = {},
        onBackClick = {},
        moveToHomeScreen = {},
    )
}

@Preview
@Composable
private fun LoginScreenPreview4() {
    val uiState = LoginUiState.getInitialValue().copy(
        email = "kanggumsil@mate.com", password = "password", invalidEmail = true
    )
    LoginScreen(
        uiState = uiState,
        snackBarMessage = SnackBarMessage.getInitValues(),
        onEmailEdit = {},
        onPasswordEdit = {},
        onShowPasswordClick = {},
        onLoginClick = {},
        onBackClick = {},
        moveToHomeScreen = {},
    )
}


@Preview
@Composable
private fun LoginScreenPreview5() {
    val uiState = LoginUiState.getInitialValue().copy(
        email = "kanggumsil@mate.com", password = "password", invalidPassword = true
    )
    LoginScreen(
        uiState = uiState,
        snackBarMessage = SnackBarMessage.getInitValues(),
        onEmailEdit = {},
        onPasswordEdit = {},
        onShowPasswordClick = {},
        onLoginClick = {},
        onBackClick = {},
        moveToHomeScreen = {},
    )
}


