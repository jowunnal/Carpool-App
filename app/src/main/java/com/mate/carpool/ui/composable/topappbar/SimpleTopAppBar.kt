package com.mate.carpool.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.mate.carpool.R
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.theme.white
import com.mate.carpool.ui.util.tu

@Composable
fun SimpleTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onBackClick: () -> Unit,
    content: @Composable RowScope.() -> Unit = {}
) {
    val density = LocalDensity.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(white)
            .padding(horizontal = 16.dp),
    ) {
        var paddingEnd by remember { mutableStateOf(32.dp) }

        Image(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable(
                    onClick = onBackClick,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false, radius = 18.dp)
                ),
            painter = painterResource(id = R.drawable.ic_arrow_left_small),
            contentDescription = "back"
        )
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 32.dp, end = paddingEnd),
            text = title,
            color = black,
            fontSize = 16.tu,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .wrapContentWidth()
                .onSizeChanged { size ->
                    val width = density.run { size.width.toDp() }
                    paddingEnd = max(width + 16.dp, paddingEnd)
                },
            verticalAlignment = Alignment.CenterVertically
        ) { content() }
    }

}

@Preview
@Composable
private fun TopAppBarPreview1() {
    SimpleTopAppBar(
        onBackClick = {},
        title = "테스트 타이틀"
    )
}

@Preview
@Composable
private fun TopAppBarPreview2() {
    SimpleTopAppBar(
        onBackClick = {},
        title = "긴 타이틀은 이렇게 말줄임 되어서 보입니다."
    ) {
        Text(text = "편집")
        HorizontalSpacer(width = 16.dp)
        Text(text = "환경설정")
    }
}
