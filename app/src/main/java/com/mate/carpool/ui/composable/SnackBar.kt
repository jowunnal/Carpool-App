package com.mate.carpool.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.R
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.theme.gray
import com.mate.carpool.ui.theme.primary20
import com.mate.carpool.ui.util.tu
import com.mate.carpool.util.MatePreview

@Composable
fun SnackBarHostCustom(
    headerMessage: String,
    contentMessage: String,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    disMissSnackBar: () -> Unit
){
    SnackbarHost(
        hostState = snackBarHostState,
        modifier = modifier,
        snackbar = {
            SnackBarCustom(
                headerMessage,
                contentMessage,
                disMissSnackBar
            )
        }
    )
}

@Composable
private fun SnackBarCustom(
    headerMessage: String,
    contentMessage: String,
    disMissSnackBar: () -> Unit
){
    Snackbar(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(78.dp),
        backgroundColor = primary20,
        shape = RoundedCornerShape(8.dp),
        action =  {
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                androidx.compose.material3.IconButton(
                    onClick = disMissSnackBar,
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_x),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        tint = gray
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = headerMessage,
                fontWeight = FontWeight.W700,
                fontSize = 16.tu,
                color = black
            )
            VerticalSpacer(height = 4.dp)
            Text(
                text = contentMessage,
                fontWeight = FontWeight.W400,
                fontSize = 13.tu,
                color = black
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSnackBarCustom(){
    MatePreview {
        SnackBarCustom(
            headerMessage = "헤더메세지는 이렇게 보입니다.",
            contentMessage = "컨텐트메세지는 이렇게 보입니다.",
            disMissSnackBar = {}
        )
    }
}