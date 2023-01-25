package com.mate.carpool.ui.screen.report.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.theme.gray
import com.mate.carpool.ui.theme.neutral40
import com.mate.carpool.ui.util.tu

@Composable
fun EtcTextField(
    value: String,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .height(157.dp)
                .border(1.dp, gray, RoundedCornerShape(4.dp))
                .padding(12.dp)
        ) {
            if (value.isEmpty()) {
                Text(
                    text = "불편했던 점들을 자세히 작성해주세요.",
                    color = gray.copy(alpha = 0.5f),
                    fontSize = 16.tu,
                    fontWeight = FontWeight.Normal
                )
            }
            BasicTextField(
                modifier = Modifier.fillMaxSize(),
                value = value,
                textStyle = TextStyle.Default.copy(
                    color = black,
                    fontSize = 16.tu,
                    fontWeight = FontWeight.Normal
                ),
                onValueChange = onValueChange,
                enabled = enabled,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions { focusManager.clearFocus() },
            )
        }
        Text(
            modifier = Modifier.align(Alignment.End),
            text = "${value.length} / 200",
            color = neutral40,
            fontWeight = FontWeight.Normal,
            fontSize = 12.tu
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EtcTextFieldPreview1() {
    EtcTextField(value = "", enabled = true, modifier = Modifier, onValueChange = {})
}

@Preview(showBackground = true)
@Composable
private fun EtcTextFieldPreview2() {
    EtcTextField(value = "이러쿵 저러쿵 어쩌구 저쩌구", enabled = true, modifier = Modifier, onValueChange = {})
}