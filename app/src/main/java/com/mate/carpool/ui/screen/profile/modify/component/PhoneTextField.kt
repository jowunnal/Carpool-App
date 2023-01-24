package com.mate.carpool.ui.screen.profile.modify.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.mate.carpool.ui.composable.LargeDefaultTextField
import com.mate.carpool.ui.composable.visualtransformation.PhoneVisualTransformation
import com.mate.carpool.util.MatePreview

@Composable
fun PhoneTextField(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    LargeDefaultTextField(
        modifier = modifier.fillMaxWidth(),
        label = "전화번호",
        value = value,
        onValueChange = onValueChange,
        placeholder = "010-1234-5678",
        visualTransformation = remember { PhoneVisualTransformation() },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions {
            focusManager.clearFocus()
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PhoneTextFieldPreview1() = MatePreview {
    PhoneTextField(value = "01011112222", onValueChange = {})
}

@Preview(showBackground = true)
@Composable
private fun PhoneTextFieldPreview2() = MatePreview {
    PhoneTextField(value = "", onValueChange = {})
}
