package com.mate.carpool.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.R
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.theme.gray
import com.mate.carpool.ui.theme.neutral20
import com.mate.carpool.ui.theme.neutral30
import com.mate.carpool.ui.theme.red50
import com.mate.carpool.ui.theme.white
import com.mate.carpool.ui.util.tu

@Composable
fun DefaultTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    placeholder: String? = null,
    errorMessage: String? = null,
    contentPadding: PaddingValues = PaddingValues(12.dp),
    headerIcon: @Composable (() -> Unit)? = null,
    tailIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
) {
    val borderColor = when {
        !enabled -> neutral30
        errorMessage != null -> red50
        else -> gray
    }
    val textColor = if (enabled) black else neutral30
    val backgroundColor = if (enabled) white else neutral20

    Column(modifier) {
        Text(
            text = label,
            color = black,
            fontWeight = FontWeight.Normal,
            fontSize = 13.tu
        )
        Row(
            modifier = Modifier
                .border(1.dp, borderColor, RoundedCornerShape(4.dp))
                .background(backgroundColor, RoundedCornerShape(4.dp))
                .padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (headerIcon != null) {
                headerIcon()
                HorizontalSpacer(width = 12.dp)
            }
            Box(modifier = Modifier.weight(1f)) {
                if (value.isEmpty() && placeholder != null) {
                    Text(
                        text = placeholder,
                        color = gray.copy(alpha = 0.5f),
                        fontSize = 16.tu,
                        fontWeight = FontWeight.Normal
                    )
                }
                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = value,
                    textStyle = TextStyle.Default.copy(
                        color = textColor,
                        fontSize = 16.tu,
                        fontWeight = FontWeight.Normal
                    ),
                    onValueChange = onValueChange,
                    enabled = enabled,
                    readOnly = readOnly,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    maxLines = maxLines
                )
            }

            if (tailIcon != null) {
                HorizontalSpacer(width = 12.dp)
                tailIcon()
            }
        }
        if (errorMessage != null) {
            Text(
                modifier = Modifier.offset(y = (-2).dp),
                text = errorMessage,
                color = red50,
                fontWeight = FontWeight.Normal,
                fontSize = 12.tu
            )
        }
    }
}

@Composable
fun SmallDefaultTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    placeholder: String? = null,
    errorMessage: String? = null,
    headerIcon: @Composable (() -> Unit)? = null,
    tailIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
) {
    DefaultTextField(
        modifier = modifier,
        label = label,
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        placeholder = placeholder,
        errorMessage = errorMessage,
        contentPadding = PaddingValues(12.dp),
        headerIcon = headerIcon,
        tailIcon = tailIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = maxLines
    )
}

@Composable
fun LargeDefaultTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    placeholder: String? = null,
    errorMessage: String? = null,
    headerIcon: @Composable (() -> Unit)? = null,
    tailIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
) {
    DefaultTextField(
        modifier = modifier,
        label = label,
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        placeholder = placeholder,
        errorMessage = errorMessage,
        contentPadding = PaddingValues(vertical = 15.dp, horizontal = 12.dp),
        headerIcon = headerIcon,
        tailIcon = tailIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = maxLines
    )
}

@Preview(showBackground = true)
@Composable
private fun DefaultTextFieldPreview1() {
    DefaultTextField(
        value = "기본 상태에서는 이렇게 보입니다.",
        label = "프리뷰",
        onValueChange = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun DefaultTextFieldPreview2() {
    DefaultTextField(
        value = "에러메시지가 있을 때는 이렇게 보입니다.",
        label = "프리뷰",
        errorMessage = "에러 메시지",
        onValueChange = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun DefaultTextFieldPreview3() {
    DefaultTextField(
        value = "disable 상태에서는 이렇게 보입니다.",
        label = "프리뷰",
        onValueChange = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun DefaultTextFieldPreview4() {
    DefaultTextField(
        value = "텍스트가 길어질 때는 이렇게 보입니다. 텍스트가 길어질 때는 이렇게 보입니다. 텍스트가 길어질 때는 이렇게 보입니다.",
        label = "프리뷰",
        onValueChange = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun DefaultTextFieldPreview5() {
    DefaultTextField(
        value = "header icon 또는 tail icon이 있을 때는 이렇게 보입니다.",
        label = "프리뷰",
        tailIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_right_small),
                contentDescription = "next button"
            )
        },
        onValueChange = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun DefaultTextFieldPreview6() {
    DefaultTextField(
        value = "disable 상태에서는 이렇게 보입니다",
        label = "프리뷰",
        enabled = false,
        onValueChange = {}
    )
}