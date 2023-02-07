package com.mate.carpool.ui.screen.ticketupdate

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import com.mate.carpool.R
import com.mate.carpool.data.model.domain.StartArea
import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.ui.composable.DropDownMenuCustom
import com.mate.carpool.ui.composable.HorizontalSpacer
import com.mate.carpool.ui.composable.LargeDefaultTextField
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.composable.button.LargePrimaryButton
import com.mate.carpool.ui.composable.layout.CommonLayout
import com.mate.carpool.ui.screen.createCarpool.fragment.TimePickerCustomDialog
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
    fragmentManager: FragmentManager,
    setStartArea: (String) -> Unit,
    setBoardingPlace: (String) -> Unit,
    setStartTime: (Long) -> Unit,
    setOpenChatLink: (String) -> Unit,
    setRecruitPersonCount: (String) -> Unit,
    setFee: (String) -> Unit,
    onNavigateToHome: () -> Unit
) {
    CommonLayout(
        title = "티켓 수정",
        onBackClick = onNavigateToHome
    ) {
        Row() {
            Column(modifier = Modifier.weight(1f)) {
                DropDownMenuCustom(
                    iconHeader = R.drawable.ic_home_location,
                    iconTail = R.drawable.ic_arrow_down_small,
                    label = "출발 지역",
                    text = ticketDetail.startArea,
                    items = StartArea.values().map { it.displayName },
                    setTextChanged = { startArea -> setStartArea(startArea) }
                )
            }
            HorizontalSpacer(width = 12.dp)
            Column(modifier = Modifier.weight(1f)) {
                Item(
                    header = "탑승 장소",
                    content = ticketDetail.boardingPlace,
                    onContentChanged = { boardingPlace -> setBoardingPlace(boardingPlace) }
                )
            }
        }
        VerticalSpacer(height = 20.dp)
        Row() {
            Column(modifier = Modifier.weight(1f)) {
                Item(
                    header = "출발 날짜",
                    content = Calendar.getInstance().run {
                        this.add(Calendar.DATE,1)
                        "내일 ${this.month}월 ${this.date}일"
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
                        val cal = Calendar.getInstance().apply {
                            add(Calendar.DATE, 1)
                            hour = timeUiState.hour
                            minute = timeUiState.min
                        }
                        setStartTime(cal.timeInMillis)
                    }.show(fragmentManager,"TimePickerDialog")
                }
            ) {
                Item(
                    header = "출발 시간",
                    content = "${ticketDetail.dayStatus.displayName} ${ticketDetail.startTime.formatStartTimeAsDisplay()}",
                    onContentChanged = {  }
                )
            }
        }
        VerticalSpacer(height = 20.dp)
        Item(
            header = "오픈 채팅방 링크",
            content = ticketDetail.openChatUrl,
            onContentChanged = { link -> setOpenChatLink(link) },
            modifier = Modifier.fillMaxWidth()
        )
        VerticalSpacer(height = 20.dp)
        Row() {
            Column(modifier = Modifier.weight(1f)) {
                DropDownMenuCustom(
                    iconTail = R.drawable.ic_arrow_down_small,
                    label = "탑승 인원",
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
                    header = "탑승 비용",
                    content = ticketDetail.ticketPrice.run {
                        DecimalFormat("###,###").format(this)
                    },
                    onContentChanged = { price -> setFee(price) }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        LargePrimaryButton(
            text = "티켓 수정하기",
            onClick = {

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
            enabled = header != "출발 날짜",
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
            ticketDetail = TicketState(
                1,
                "2017",
                "",
                "황진호",
                "인동",
                "경운대학교",
                "경운대학교앞",
                DayStatus.AM,
                25200L,
                "link",
                3,
                20000,
                passenger = emptyList()
            ),
            fragmentManager = object : FragmentManager(){},
            setStartArea = {},
            setBoardingPlace = {},
            setStartTime = {},
            setOpenChatLink = {},
            setRecruitPersonCount = {},
            setFee = {},
            onNavigateToHome = {}
        )
    }
}

