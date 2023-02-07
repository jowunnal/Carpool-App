package com.mate.carpool.ui.screen.register

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.mate.carpool.ui.composable.LargeDefaultTextField
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.composable.button.PrimaryButton
import com.mate.carpool.ui.composable.layout.CommonLayout
import com.mate.carpool.ui.screen.register.item.RegisterUiState
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.util.FileUtils
import com.mate.carpool.ui.util.tu
import com.mate.carpool.util.MatePreview
import okhttp3.MultipartBody

@Composable
fun RegisterDriverStepPhoneNumberScreen(
    uiState: RegisterUiState,
    onPhoneNumberEdit: (String) -> Unit,
    onCarNumberEdit: (String) -> Unit,
    onNavigatePopBackStack: () -> Unit,
    onFetch: (MultipartBody.Part) -> Unit
) {
    val context = LocalContext.current
    val textFieldFocusRequest = remember { FocusRequester() }

    CommonLayout(
        title = "드라이버 등록",
        onBackClick = onNavigatePopBackStack
    ) {
        Text(
            text = "연락을 받을 수 있는\n전화번호를 입력해주세요.",
            fontSize = 22.tu,
            fontWeight = FontWeight.W700,
            color = black
        )
        VerticalSpacer(height = 30.dp)
        LargeDefaultTextField(
            modifier = Modifier.focusRequester(textFieldFocusRequest),
            label = "전화 번호",
            value = uiState.phoneNumber,
            onValueChange = onPhoneNumberEdit,
            placeholder = "‘-’없이 11자리를 입력해주세요.",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )
        VerticalSpacer(height = 24.dp)
        LargeDefaultTextField(
            modifier = Modifier.focusRequester(textFieldFocusRequest),
            label = "차량 번호",
            value = uiState.carNumber,
            onValueChange = onCarNumberEdit,
            placeholder = "띄어쓰기 없이 입력해주세요",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(
            text = "드라이버 등록하기",
            onClick = {
                onFetch(
                    FileUtils.getImageMultipartBody(
                        uri = uiState.carImage?:"".toUri(),
                        context = context
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.invalidPhoneNumber and uiState.invalidCarNumber
        )
    }
}

@Composable
@Preview
private fun PreviewRegisterDriverStepPhoneNumberScreen(){
    MatePreview {
        RegisterDriverStepPhoneNumberScreen(
            uiState = RegisterUiState.getInitValue(),
            onPhoneNumberEdit = {},
            onCarNumberEdit = {},
            onNavigatePopBackStack = {},
            onFetch = {}
        )
    }
}