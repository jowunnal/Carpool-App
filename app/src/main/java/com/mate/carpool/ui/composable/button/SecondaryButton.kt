package com.mate.carpool.ui.composable.button

import androidx.compose.foundation.border
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
import com.mate.carpool.ui.util.tu
import com.mate.carpool.util.MatePreview

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enabledTextColor: Color = primary50,
    enabledBorderColor: Color = enabledTextColor,
    contentPadding: PaddingValues = PaddingValues(horizontal = 20.dp, vertical = 13.dp),
    fontSize: TextUnit = 18.tu,
) {
    val textColor = if (enabled) enabledTextColor else neutral20
    val borderColor = if (enabled) enabledBorderColor else neutral30

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .border(1.dp, borderColor, RoundedCornerShape(100.dp))
            .clickable(
                onClick = onClick,
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null
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
fun LargeSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    enabledBorderColor: Color = primary50,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    SecondaryButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        fontSize = 18.tu,
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 13.dp),
        enabled = enabled,
        enabledBorderColor = enabledBorderColor,
        interactionSource = interactionSource
    )
}

@Composable
fun MediumSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    enabledBorderColor: Color = primary50,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    SecondaryButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        fontSize = 16.tu,
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 1.dp),
        enabled = enabled,
        enabledBorderColor = enabledBorderColor,
        interactionSource = interactionSource
    )
}

@Composable
fun SmallSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    enabledBorderColor: Color = primary50,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    SecondaryButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        fontSize = 14.tu,
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
        enabled = enabled,
        enabledBorderColor = enabledBorderColor,
        interactionSource = interactionSource
    )
}

@Preview
@Composable
private fun SecondaryButtonPreview1() = MatePreview {
    SecondaryButton(
        text = "기본 상태에서는 이렇게 보입니다.",
        onClick = {}
    )
}

@Preview
@Composable
private fun SecondaryButtonPreview2() = MatePreview {
    SecondaryButton(
        text = "disabled일 때는 이렇게 보입니다.",
        onClick = {},
        enabled = false
    )
}
