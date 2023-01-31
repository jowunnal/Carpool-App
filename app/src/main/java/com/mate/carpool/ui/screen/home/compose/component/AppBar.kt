package com.mate.carpool.ui.screen.home.compose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.R
import com.mate.carpool.ui.theme.MateTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    profileImage:String,
    goToProfileScreen: () -> Unit,
    onOpenDrawer: suspend () -> Unit
){
    val coroutineScope = rememberCoroutineScope()
    SmallTopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_mate),
                contentDescription = null
            )
        },
        navigationIcon = {
            /*TODO()*/
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {
                ProfileImage(
                    profileImage = profileImage,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.White, CircleShape)
                        .clickable(onClick = goToProfileScreen),
                    defaultImage = R.drawable.ic_profile
                )
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            onOpenDrawer()
                        }
                    },
                    Modifier
                        .width(36.dp)
                        .height(36.dp)
                )
                {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_hamburger),
                        contentDescription = null,
                        Modifier.fillMaxSize()
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .wrapContentHeight(),
        colors = TopAppBarDefaults.smallTopAppBarColors(Color.White)
    )
}

@Preview
@Composable
private fun PrevHomeAppBar() =
    MateTheme {
        HomeAppBar(
            "",
            goToProfileScreen = {},
            onOpenDrawer = {}
        )
    }