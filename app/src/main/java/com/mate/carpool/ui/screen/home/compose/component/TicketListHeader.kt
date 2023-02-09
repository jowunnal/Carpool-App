package com.mate.carpool.ui.screen.home.compose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.R
import com.mate.carpool.ui.composable.HorizontalSpacer
import com.mate.carpool.ui.theme.MateTheme
import com.mate.carpool.ui.util.tu

@Composable
fun TicketListHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp
            )
            .height(44.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_home_list),
            contentDescription = null,
            Modifier
                .width(24.dp)
                .height(24.dp)
        )

        HorizontalSpacer(width = 8.dp)

        Text(
            text = "카풀 목록",
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.tu
        )

        Image(
            painter = painterResource(id = R.drawable.ic_home_reddot),
            contentDescription = null
        )

        Text(
            text = "유료",
            fontSize = 12.tu
        )

        HorizontalSpacer(width = 6.dp)

        Image(
            painter = painterResource(id = R.drawable.ic_home_bluedot),
            contentDescription = null
        )

        Text(
            text = "무료",
            fontSize = 12.tu
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTicketHeader() =
    MateTheme {
        TicketListHeader()
    }