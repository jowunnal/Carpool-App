package com.mate.carpool.ui.screen.home.compose.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.R
import com.mate.carpool.ui.theme.MateTheme
import com.mate.carpool.ui.util.tu

@Composable
fun HomeCardView(
    @DrawableRes imageId: Int,
    text: String,
    @DrawableRes icon: Int,
    onNavigateCallBack: () -> Unit
) {
    ElevatedCard(
        shape = RoundedCornerShape(7.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                fontSize = 16.tu,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black, modifier = Modifier
                    .weight(1f)
                    .height(22.dp)
            )

            IconButton(
                onClick = onNavigateCallBack,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeMenu() =
    MateTheme {
        HomeCardView(
            imageId = R.drawable.ic_home_folder,
            text = "공지사항",
            icon = R.drawable.ic_navigate_next_small,
            onNavigateCallBack = {}
        )
    }