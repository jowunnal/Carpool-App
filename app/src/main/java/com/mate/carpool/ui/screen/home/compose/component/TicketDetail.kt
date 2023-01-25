package com.mate.carpool.ui.screen.home.compose.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.ui.theme.primary50
import com.mate.carpool.ui.theme.red50
import com.mate.carpool.ui.utils.IntUtils.toSp

@Composable
fun HomeTicketDetail(
    text1: String,
    text2: String,
    text3: String,
    text4: String
) {
    Row(
        Modifier
            .height(34.dp)
            .fillMaxWidth()
    )
    {
        Column(
            Modifier
                .weight(1f)
        )
        {
            Text(
                text = text1,
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                fontSize = 12.toSp(),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = text2,
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                fontSize = 14.toSp()
            )
        }
        Column(
            Modifier
                .weight(1f)
        )
        {
            Text(
                text = text3,
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                fontSize = 12.toSp(),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = text4,
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = when (text4) {
                    "무료" -> primary50
                    "유료" -> red50
                    else -> Color.Black
                },
                fontSize = 14.toSp()
            )
        }
    }
}

@Preview
@Composable
fun PreviewHomeTicketDetail(){
    HomeTicketDetail(
        text1 = "출발 시간",
        text2 = "오전 11시,1월 21일",
        text3 = "탑승 장소",
        text4 = "인동"
    )
}