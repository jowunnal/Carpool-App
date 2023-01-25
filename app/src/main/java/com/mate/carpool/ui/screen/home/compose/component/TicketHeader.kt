package com.mate.carpool.ui.screen.home.compose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.R

@Composable
fun TicketHeader(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
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
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "카풀 목록",
            modifier = Modifier.weight(1f)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_home_reddot),
            contentDescription = null
        )
        Text(text = "유료")
        Spacer(modifier = Modifier.width(6.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_home_bluedot),
            contentDescription = null
        )
        Text(text = "무료")
    }
}

@Preview
@Composable
fun PreviewTicketHeader(){
    TicketHeader()
}