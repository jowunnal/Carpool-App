package com.mate.carpool.ui.screen.profile.lookup.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.mate.carpool.ui.composable.SimpleTopAppBar
import com.mate.carpool.ui.theme.MateTheme
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.util.tu

@Composable
fun TopAppBar(
    name: String,
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit,
    onBackClick: () -> Unit
) {
    SimpleTopAppBar(
        modifier = modifier,
        title = "${name}님의 정보",
        onBackClick = onBackClick,
    ) {
        Text(
            modifier = Modifier.clickable(onClick = onEditClick),
            text = "수정",
            color = black,
            fontSize = 16.tu,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TopAppBarPreview() {
    MateTheme {
        TopAppBar(name = "강금실", onEditClick = {}, onBackClick = {})
    }
}
