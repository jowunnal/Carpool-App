package com.mate.carpool.ui.screen.report.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.R
import com.mate.carpool.ui.composable.HorizontalSpacer
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.util.tu

@Composable
fun ReasonOption(
    reason: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onSelect: () -> Unit,
    onDeselect: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = if (selected) onDeselect else onSelect),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = if (selected) {
                painterResource(id = R.drawable.ic_check_24_blue)
            } else {
                painterResource(id = R.drawable.ic_check_24_gray)
            },
            contentDescription = "select button"
        )
        HorizontalSpacer(width = 4.dp)
        Text(
            text = reason,
            color = black,
            fontSize = 16.tu,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReasonOptionPreview1() {
    ReasonOption(
        reason = "카풀 돈을 받지 못했어요",
        selected = true,
        modifier = Modifier,
        onSelect = {},
        onDeselect = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun ReasonOptionPreview2() {
    ReasonOption(
        reason = "카풀 돈을 받지 못했어요",
        selected = false,
        modifier = Modifier,
        onSelect = {},
        onDeselect = {}
    )
}