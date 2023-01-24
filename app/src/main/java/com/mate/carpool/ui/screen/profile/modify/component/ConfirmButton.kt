package com.mate.carpool.ui.screen.profile.modify.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.composable.button.LargePrimaryButton
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.util.tu
import com.mate.carpool.util.MatePreview

@Composable
fun ConfirmButton(enabled: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = buildAnnotatedString {
                append("이름, 학과 및 학번은 변경이 불가능합니다\n변경이 필요하면 ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("\'문의하기\'")
                }
                append("에서 신청해주세요.")
            },
            color = black,
            fontSize = 13.tu,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
        VerticalSpacer(height = 12.dp)
        LargePrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = "수정완료",
            enabled = enabled,
            onClick = onClick
        )
    }
}

@Preview
@Composable
private fun ConfirmButtonPreview() = MatePreview {
    ConfirmButton(enabled = true, onClick = {},)
}