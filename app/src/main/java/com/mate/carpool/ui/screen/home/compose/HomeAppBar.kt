package com.mate.carpool.ui.screen.home.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.mate.carpool.R
import com.mate.carpool.ui.theme.Colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(profileImage:String){
    SmallTopAppBar(
        title = { Text(text = "MATE",
            color = Colors.Blue_007AFF,
            textAlign = TextAlign.Center,
            fontSize = 28.sp,
            fontWeight = FontWeight.W900,
            modifier = Modifier
                .wrapContentSize(),
            style = LocalTextStyle.current.merge(
                TextStyle(
                    lineHeight = 1.em,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    ),
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.None
                    )
                )
            ))
        },
        navigationIcon = {
            /*TODO()*/
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center)
            {
                if(profileImage!="preview")
                    ProfileImage(
                        profileImage = profileImage,
                        modifier =  Modifier
                            .size(42.dp)
                            .padding(3.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.White, CircleShape)
                    )
                IconButton(
                    onClick = { /*TODO*/ },
                    Modifier
                        .width(36.dp)
                        .height(36.dp)
                        .padding(4.5.dp))
                {
                    Icon(painter = painterResource(id = R.drawable.icon_hamburger),
                        contentDescription = null, Modifier.fillMaxSize())
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth(),
        colors = TopAppBarDefaults.smallTopAppBarColors(Color.White)
    )
}

@Preview
@Composable
fun PrevHomeAppBar(){
    HomeAppBar("preview")
}