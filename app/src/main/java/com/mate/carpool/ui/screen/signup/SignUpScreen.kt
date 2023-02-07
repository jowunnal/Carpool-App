package com.mate.carpool.ui.screen.signup

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.R
import com.mate.carpool.ui.composable.LargeDefaultTextField
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.composable.button.LargePrimaryButton
import com.mate.carpool.ui.composable.layout.CommonLayout
import com.mate.carpool.ui.screen.signup.item.SignUpUiState
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.theme.neutral40
import com.mate.carpool.ui.theme.primary90
import com.mate.carpool.ui.util.tu

@Composable
fun SignUpScreen(
    uiState: SignUpUiState,
    onNameEdit: (String) -> Unit,
    onEmailEdit: (String) -> Unit,
    onPasswordEdit: (String) -> Unit,
    onShowPasswordClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onBackClick: () -> Unit,
    moveToHomeScreen: () -> Unit,
) {
    val emailTextFieldFocusRequest = remember { FocusRequester() }
    val passwordTextFieldFocusRequest = remember { FocusRequester() }

    CommonLayout(
        title = null,
        onBackClick = onBackClick
    ) {
        Text(
            text = "회원가입을 위해\n정보를 입력해주세요.",
            color = black,
            fontWeight = FontWeight.Bold,
            fontSize = 22.tu
        )
        VerticalSpacer(height = 30.dp)
        LargeDefaultTextField(
            label = "이름",
            value = uiState.name,
            onValueChange = onNameEdit,
            placeholder = "예: 메이트",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions {
                emailTextFieldFocusRequest.requestFocus()
            }
        )
        VerticalSpacer(height = 30.dp)
        LargeDefaultTextField(
            label = "이메일",
            value = uiState.email,
            onValueChange = onEmailEdit,
            placeholder = "예: matecarpool@gmail.com",
            errorMessage = if (uiState.invalidEmail) "다른 이메일 혹은 이메일 형식에 맞추어 입력해주세요." else null,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions {
                passwordTextFieldFocusRequest.requestFocus()
            }
        )
        VerticalSpacer(height = 30.dp)
        LargeDefaultTextField(
            modifier = Modifier.focusRequester(passwordTextFieldFocusRequest),
            label = "비밀번호",
            value = uiState.password,
            onValueChange = onPasswordEdit,
            placeholder = "비밀번호를 입력해주세요.",
            errorMessage = if (uiState.invalidPassword) {
                "영문과 숫자를 조합하여 8-20자사이로 입력해주세요.\n" +
                        "최소 1개 이상의 숫자, 소문자, 대문자로 구성해주세요."
            } else null,
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
        if (!uiState.invalidPassword) {
            Text(
                modifier = Modifier,
                text = "영문과 숫자를 조합하여 8-20자사이로 입력해주세요.\n" +
                        "최소 1개 이상의 숫자, 소문자, 대문자로 구성해주세요.",
                color = if (uiState.password.isEmpty()) neutral40 else primary90,
                fontWeight = FontWeight.Normal,
                fontSize = 12.tu
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        LargePrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = "회원가입하기",
            onClick = onSignUpClick,
            enabled = uiState.enableSignUp,
        )
    }

    LaunchedEffect(key1 = uiState.signUpSuccess) {
        if (uiState.signUpSuccess) {
            moveToHomeScreen()
        }
    }
}

@Preview
@Composable
private fun SignUpScreenPreview1() {
    val uiState = SignUpUiState.getInitialValue()
    SignUpPreviewScreen(uiState = uiState)
}

@Preview
@Composable
private fun SignUpScreenPreview2() {
    val uiState = SignUpUiState.getInitialValue().copy(password = "password")
    SignUpPreviewScreen(uiState = uiState)
}

@Preview
@Composable
private fun SignUpScreenPreview3() {
    val uiState = SignUpUiState.getInitialValue().copy(
        name = "강금실", email = "kanggumsil@mate.com", password = "password", showPassword = true
    )
    SignUpPreviewScreen(uiState = uiState)
}

@Preview
@Composable
private fun SignUpScreenPreview4() {
    val uiState = SignUpUiState.getInitialValue().copy(
        email = "kanggumsil@mate.com", password = "password", invalidEmail = true
    )
    SignUpPreviewScreen(uiState = uiState)
}


@Preview
@Composable
private fun SignUpScreenPreview5() {
    val uiState = SignUpUiState.getInitialValue().copy(
        email = "kanggumsil@mate.com", password = "password", invalidPassword = true
    )
    SignUpPreviewScreen(uiState = uiState)
}

@VisibleForTesting
@Composable
private fun SignUpPreviewScreen(uiState: SignUpUiState) {
    SignUpScreen(
        uiState = uiState,
        onNameEdit = {},
        onEmailEdit = {},
        onPasswordEdit = {},
        onShowPasswordClick = {},
        onSignUpClick = {},
        onBackClick = {},
        moveToHomeScreen = {},
    )
}
