package com.mate.carpool.ui.screen.home.compose.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.mate.carpool.data.model.item.MemberRole
import com.mate.carpool.ui.base.BaseViewModel
import com.mate.carpool.ui.composable.DialogState
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.util.tu

@Composable
fun PopupWindow(
    dialogState: Boolean,
    popUpOffset: Offset,
    userRole: MemberRole,
    selectedMemberPassengerId: String,
    deletePassengerFromTicket: (String,MemberRole) -> Unit,
    onNavigateToReportView: () -> Unit,
    onRefresh: (String) -> Unit,
    onOpenDialog: (DialogState) -> Unit,
    onCloseDialog: () -> Unit
) {
    if (dialogState) {
        Popup(
            popupPositionProvider = object : PopupPositionProvider {
                override fun calculatePosition(
                    anchorBounds: IntRect,
                    windowSize: IntSize,
                    layoutDirection: LayoutDirection,
                    popupContentSize: IntSize
                ): IntOffset {
                    return IntOffset(
                        x = popUpOffset.x.toInt() - (popupContentSize.width),
                        y = popUpOffset.y.toInt() + (popupContentSize.height / 2)
                    )
                }
            },
            properties = PopupProperties(),
            onDismissRequest = {}
        ) {
            Column(
                Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp),
                    )
                    .shadow(0.5.dp)

            ) {
                if (userRole == MemberRole.DRIVER)
                    PopUpItem(
                        text = "????????????",
                        modifier = Modifier
                            .width(100.dp)
                            .height(36.dp)
                            .padding(
                                vertical = 8.dp,
                                horizontal = 12.dp
                            )
                            .clickable {
                                onOpenDialog(
                                    DialogState(
                                        header = "?????? ????????? ??????????",
                                        content = "????????? ?????? ?????? ????????? ????????? ?????? ????????? ????????????. ?????? ???????????? ?????? ?????? ??? ??????????????? ???????????????.",
                                        positiveMessage = "???, ???????????????.",
                                        negativeMessage = "??????, ???????????????.",
                                        onPositiveCallback = {
                                            deletePassengerFromTicket(selectedMemberPassengerId,MemberRole.DRIVER)
                                            onRefresh(BaseViewModel.EVENT_READY)
                                            onCloseDialog()
                                        },
                                        onNegativeCallback = onCloseDialog
                                    )
                                )
                            }
                    )

                PopUpItem(
                    text = "????????????",
                    modifier = Modifier
                        .width(100.dp)
                        .height(36.dp)
                        .padding(
                            vertical = 8.dp,
                            horizontal = 12.dp
                        )
                        .clickable {
                            onNavigateToReportView()
                            onRefresh(BaseViewModel.EVENT_READY)
                        }
                )
            }
        }
    }
}

@Composable
private fun PopUpItem(
    text: String,
    modifier: Modifier
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = text,
            fontSize = 14.tu,
            fontWeight = FontWeight.W400,
            color = black,
            modifier = Modifier.weight(1f)
        )
    }
}