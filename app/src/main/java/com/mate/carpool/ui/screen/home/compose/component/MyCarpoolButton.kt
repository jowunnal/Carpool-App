package com.mate.carpool.ui.screen.home.compose.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.composable.button.PrimaryButton
import com.mate.carpool.util.MatePreview
import kotlinx.coroutines.launch

@Composable
fun MyCarpoolButton(
    carpoolExistState: Boolean ,
    getMyTicketDetail: () -> Unit,
    onOpenBottomSheet: suspend () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    if(carpoolExistState) {
        VerticalSpacer(height = 50.dp)
        PrimaryButton(
            text = "내 카풀 보기",
            onClick = {
                coroutineScope.launch {
                    getMyTicketDetail()
                    onOpenBottomSheet()
                }
            },
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
        )
        VerticalSpacer(height = 22.dp)
    }
}

@Preview
@Composable
private fun PreviewMyCarpoolButton() =
    MatePreview {
        MyCarpoolButton(
            carpoolExistState = true,
            onOpenBottomSheet = {},
            getMyTicketDetail = {}
        )
    }