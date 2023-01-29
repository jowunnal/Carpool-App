package com.mate.carpool.ui.composable.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.mate.carpool.ui.theme.neutral20
import com.mate.carpool.ui.theme.neutral30
import com.mate.carpool.ui.theme.primary50
import com.mate.carpool.ui.theme.white
import com.mate.carpool.ui.util.tu
import com.mate.carpool.util.MatePreview

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enabledBackgroundColor: Color = primary50,
    contentPadding: PaddingValues = PaddingValues(horizontal = 20.dp, vertical = 13.dp),
    fontSize: TextUnit = 18.tu,
) {
    val backgroundColor = if (enabled) enabledBackgroundColor else neutral20
    val textColor = if (enabled) white else neutral30

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .background(backgroundColor)
            .clickable(
                onClick = onClick,
                enabled = enabled,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.padding(contentPadding),
            text = text,
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize,
            maxLines = 1
        )
    }
}

@Composable
fun LargePrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    enabledBackgroundColor: Color = primary50,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    PrimaryButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        fontSize = 18.tu,
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 13.dp),
        enabled = enabled,
        enabledBackgroundColor = enabledBackgroundColor,
        interactionSource = interactionSource
    )
}

@Composable
fun MediumPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    enabledBackgroundColor: Color = primary50,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    PrimaryButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        fontSize = 16.tu,
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
        enabled = enabled,
        enabledBackgroundColor = enabledBackgroundColor,
        interactionSource = interactionSource
    )
}

@Composable
fun SmallPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    enabledBackgroundColor: Color = primary50,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    PrimaryButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        fontSize = 14.tu,
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
        enabled = enabled,
        enabledBackgroundColor = enabledBackgroundColor,
        interactionSource = interactionSource
    )
}

@Preview
@Composable
private fun PrimaryButtonPreview1() = MatePreview {
    PrimaryButton(
        text = "기본 상태에서는 이렇게 보입니다.",
        onClick = {}
    )
}

@Preview
@Composable
private fun PrimaryButtonPreview2() = MatePreview {
    PrimaryButton(
        text = "disabled일 때는 이렇게 보입니다.",
        onClick = {},
        enabled = false
    )
}
