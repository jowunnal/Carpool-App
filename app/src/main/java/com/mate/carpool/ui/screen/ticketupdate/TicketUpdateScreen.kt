package com.mate.carpool.ui.screen.ticketupdate

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.SnackbarDuration
import androidx.compose.material3.Icon
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import com.mate.carpool.R
import com.mate.carpool.data.model.domain.StartArea
import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.data.model.item.MemberRole
import com.mate.carpool.ui.base.SnackBarMessage
import com.mate.carpool.ui.composable.*
import com.mate.carpool.ui.composable.button.LargePrimaryButton
import com.mate.carpool.ui.composable.layout.CommonLayout
import com.mate.carpool.ui.screen.createCarpool.fragment.TimePickerCustomDialog
import com.mate.carpool.ui.screen.home.item.DriverState
import com.mate.carpool.ui.screen.home.item.PassengerState
import com.mate.carpool.ui.screen.home.item.TicketState
import com.mate.carpool.ui.theme.neutral30
import com.mate.carpool.ui.util.date
import com.mate.carpool.ui.util.hour
import com.mate.carpool.ui.util.minute
import com.mate.carpool.ui.util.month
import com.mate.carpool.util.MatePreview
import com.mate.carpool.util.formatStartTimeAsDisplay
import java.text.DecimalFormat
import java.util.*

@Composable
fun TicketUpdateScreen(
    ticketDetail: TicketState,
    snackBarMessage: SnackBarMessage,
    fragmentManager: FragmentManager,
    setStartArea: (String) -> Unit,
    setBoardingPlace: (String) -> Unit,
    setStartTime: (Long) -> Unit,
    setOpenChatLink: (String) -> Unit,
    setRecruitPersonCount: (String) -> Unit,
    setFee: (String) -> Unit,
    updateTicket: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    if(snackBarMessage != SnackBarMessage.getInitValues())
        LaunchedEffect(key1 = snackBarMessage.headerMessage) {
            snackBarHostState.showSnackbar(
                message = snackBarMessage.headerMessage,
                actionLabel = snackBarMessage.contentMessage,
                duration = SnackbarDuration.Indefinite
            )
        }

    CommonLayout(
        title = "?????? ??????",
        onBackClick = onNavigateToHome,
        snackBarHost = {
            Column() {
                SnackBarHostCustom(headerMessage = it.currentSnackbarData?.message ?: "",
                    contentMessage = it.currentSnackbarData?.actionLabel ?: "",
                    snackBarHostState = snackBarHostState,
                    disMissSnackBar = { snackBarHostState.currentSnackbarData?.dismiss() })
                VerticalSpacer(height = 90.dp)
            }
        }
    ) {
        Row() {
            Column(modifier = Modifier.weight(1f)) {
                DropDownMenuCustom(
                    iconHeader = R.drawable.ic_home_location,
                    iconTail = R.drawable.ic_arrow_down_small,
                    label = "?????? ??????",
                    text = ticketDetail.startArea,
                    items = StartArea.values().map { it.displayName },
                    setTextChanged = { startArea -> setStartArea(startArea) }
                )
            }
            HorizontalSpacer(width = 12.dp)
            Column(modifier = Modifier.weight(1f)) {
                Item(
                    header = "?????? ??????",
                    content = ticketDetail.boardingPlace,
                    onContentChanged = { boardingPlace -> setBoardingPlace(boardingPlace) }
                )
            }
        }
        VerticalSpacer(height = 20.dp)
        Row() {
            Column(modifier = Modifier.weight(1f)) {
                Item(
                    header = "?????? ??????",
                    content = Calendar.getInstance().run {
                        this.add(Calendar.DATE,1)
                        "?????? ${this.month}??? ${this.date}???"
                    },
                    onContentChanged = { },
                    headerIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            contentDescription = null,
                            tint = neutral30
                        )
                    }
                )
            }
            HorizontalSpacer(width = 12.dp)
            Column(modifier = Modifier
                .weight(1f)
                .clickable {
                    TimePickerCustomDialog { timeUiState ->
                        val cal = Calendar
                            .getInstance()
                            .apply {
                                add(Calendar.DATE, 1)
                                hour = timeUiState.hour
                                minute = timeUiState.min
                            }
                        setStartTime(cal.timeInMillis)
                    }.show(fragmentManager, "TimePickerDialog")
                }
            ) {
                Item(
                    header = "?????? ??????",
                    content = "${ticketDetail.dayStatus.displayName} ${ticketDetail.startTime.formatStartTimeAsDisplay()}",
                    onContentChanged = {  }
                )
            }
        }
        VerticalSpacer(height = 20.dp)
        Item(
            header = "?????? ????????? ??????",
            content = ticketDetail.openChatUrl,
            onContentChanged = { link -> setOpenChatLink(link) },
            modifier = Modifier.fillMaxWidth()
        )
        VerticalSpacer(height = 20.dp)
        Row() {
            Column(modifier = Modifier.weight(1f)) {
                DropDownMenuCustom(
                    iconTail = R.drawable.ic_arrow_down_small,
                    label = "?????? ??????",
                    text = ticketDetail.recruitPerson.toString(),
                    items = listOf("1","2","3","4"),
                    setTextChanged = { num -> setRecruitPersonCount(num) }
                )
            }
            HorizontalSpacer(width = 12.dp)
            Column(modifier = Modifier
                .weight(1f)
                .clickable { }) {
                Item(
                    header = "?????? ??????",
                    content = ticketDetail.ticketPrice.run {
                        DecimalFormat("###,###").format(this)
                    },
                    onContentChanged = { price -> setFee(price) }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        LargePrimaryButton(
            text = "?????? ????????????",
            onClick = {
                updateTicket()
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun Item(
    header: String,
    content: String,
    headerIcon: @Composable (() -> Unit)? = null,
    onContentChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column() {
        LargeDefaultTextField(
            label = header,
            value = content,
            onValueChange = onContentChanged,
            enabled = header != "?????? ??????",
            headerIcon = headerIcon,
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTicketUpdate() {
    MatePreview {
        TicketUpdateScreen(
            TicketState(
                id = "1",
                startArea = "?????????",
                endArea = "???????????????",
                boardingPlace = "??????????????????",
                dayStatus = DayStatus.AM,
                startTime = 25200L,
                openChatUrl = "",
                recruitPerson = 3,
                ticketPrice = 2000,
                driver = DriverState(
                    id = "202",
                    name = "?????????",
                    profileImage = "",
                    email = "",
                    role = MemberRole.DRIVER,
                    phoneNumber = "",
                    carNumber = "",
                    carImage = ""
                ),
                passenger = emptyList()
            ),
            snackBarMessage = SnackBarMessage.getInitValues(),
            fragmentManager = object : FragmentManager(){},
            setStartArea = {},
            setBoardingPlace = {},
            setStartTime = {},
            setOpenChatLink = {},
            setRecruitPersonCount = {},
            setFee = {},
            updateTicket = {},
            onNavigateToHome = {}
        )
    }
}

