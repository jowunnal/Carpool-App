package com.mate.carpool.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mate.carpool.ui.theme.red30

@Composable
fun VerticalSpacer(
    height: Dp,
    modifier: Modifier = Modifier,
) {
    Spacer(modifier.height(height))
}

@Suppress("FunctionName")
fun LazyListScope.VerticalSpacerItem(height: Dp) {
    item { VerticalSpacer(height) }
}

@Composable
fun HorizontalSpacer(
    width: Dp,
    modifier: Modifier = Modifier,
) {
    Spacer(modifier.width(width))
}

@Suppress("FunctionName")
fun LazyListScope.HorizontalSpacerItem(width: Dp) {
    item { HorizontalSpacer(width) }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
private fun VerticalSpacerPreview() {
    Column {
        Box(Modifier.fillMaxWidth().weight(1f).background(red30))
        VerticalSpacer(20.dp)
        Box(Modifier.fillMaxWidth().weight(1f).background(red30))
    }

}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
private fun HorizontalSpacerPreview() {
    Row {
        Box(Modifier.fillMaxHeight().weight(1f).background(red30))
        HorizontalSpacer(20.dp)
        Box(Modifier.fillMaxHeight().weight(1f).background(red30))
    }
}