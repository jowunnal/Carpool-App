package com.mate.carpool.ui.screen.home.compose.component.drawer

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mate.carpool.R
import com.mate.carpool.ui.theme.*
import com.mate.carpool.ui.util.tu
import kotlinx.coroutines.launch

enum class DrawerItem(
    val title: String,
    val route: String
) {
    CONTACT(
        title = "문의하기",
        route = "contact"
    ),
    CLAUSE(
        title = "서비스 책임 조항",
        route = "clause"
    ),
    LOGOUT(
        title = "로그아웃",
        route = "logout"
    ),
    WITHDRAW(
        title = "회원탈퇴",
        route = "withdraw"
    )
}

@Composable
fun DrawerContent(
    items: List<DrawerItem>,
    onCloseDrawer: suspend () -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Column() {
            Header(onCloseDrawer)
            Column(modifier = Modifier.weight(1f)) {
                for (item in items) {
                    Body(item = item)
                }
            }
            Tail()
        }
    }
}

@Composable
private fun Header(
    onCloseDrawer: suspend () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            onClick = {
                coroutineScope.launch {
                    onCloseDrawer()
                }
            },
            modifier = Modifier
                .size(36.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_x),
                contentDescription = "DrawerExitIcon",
                modifier = Modifier.fillMaxSize(),
                tint = neutral50
            )
        }
    }
}

@Composable
private fun Body(
    item: DrawerItem
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        when (item) {
            DrawerItem.WITHDRAW -> {
                Text(
                    text = item.title,
                    fontSize = 16.tu,
                    fontWeight = FontWeight.W700,
                    color = neutral30
                )
            }
            else -> {
                Text(
                    text = item.title,
                    fontSize = 16.tu,
                    fontWeight = FontWeight.W700,
                    color = black
                )
            }
        }
    }
}

@Composable
private fun Tail() {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "앱 버전 1.02",
            fontSize = 13.tu,
            fontWeight = FontWeight.W400
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDrawerContent() {
    MateTheme() {
        DrawerContent(
            items = listOf(
                DrawerItem.CONTACT,
                DrawerItem.CLAUSE,
                DrawerItem.LOGOUT,
                DrawerItem.WITHDRAW
            ),
            onCloseDrawer = {}
        )
    }
}