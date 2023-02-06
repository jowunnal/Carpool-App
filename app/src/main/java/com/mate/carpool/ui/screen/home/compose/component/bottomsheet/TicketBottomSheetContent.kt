package com.mate.carpool.ui.screen.home.compose.component.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.R
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.domain.UserModel
import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.data.model.item.MemberRole
import com.mate.carpool.data.model.item.TicketType
import com.mate.carpool.ui.base.SnackBarMessage
import com.mate.carpool.ui.composable.HorizontalSpacer
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.composable.button.PrimaryButton
import com.mate.carpool.ui.screen.home.compose.component.ProfileImage
import com.mate.carpool.ui.screen.home.vm.HomeBottomSheetViewModel
import com.mate.carpool.ui.theme.*
import com.mate.carpool.ui.util.tu
import com.mate.carpool.util.formatStartDayMonthToDTO
import com.mate.carpool.util.formatStartTimeToDTO
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@Composable
fun TicketBottomSheetContent(
    ticketDetail: TicketModel,
    userProfile: String,
    userRole: MemberRole,
    userTicketList: List<TicketListModel>,
    addNewPassengerToTicket: (Long) -> Unit,
    onRefresh: (String) -> Unit,
    onCloseBottomSheet: suspend () -> Unit
){
    val coroutineScope = rememberCoroutineScope()

    Column(
        Modifier
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            )
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_handle_bar)
                , contentDescription = "HandleBar"
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick =
                {
                    coroutineScope.launch {
                        onCloseBottomSheet()
                    }
                },
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_x),
                    contentDescription = null
                )
            }
        }
        VerticalSpacer(height = 16.dp)
        TicketHeader(
            ticketDetail.startArea,
            ticketDetail.endArea
        )
        VerticalSpacer(height = 16.dp)

        Driver(
            userProfile = userProfile,
            memberName = ticketDetail.memberName
        )

        VerticalSpacer(height = 20.dp)
        TicketDetail(
            text1 = "출발 시간",
            text2 = "${ticketDetail.startTime.formatStartTimeToDTO()},${ticketDetail.startTime.formatStartDayMonthToDTO()}",
            text3 = "탑승 장소",
            text4 = ticketDetail.boardingPlace
        )
        VerticalSpacer(height = 20.dp)
        TicketDetail(
            text1 = "탑승 인원",
            text2 = "${ticketDetail.passenger?.size?:0}/${ticketDetail.recruitPerson}",
            text3 = "비용",
            text4 = DecimalFormat("###,###").format(ticketDetail.ticketPrice)
        )
        VerticalSpacer(height = 20.dp)

        when(userRole) {
            MemberRole.DRIVER -> {
                TicketButtonMessage(message = "드라이버는 다른 카풀에 탑승할 수 없어요.")
            }
            MemberRole.PASSENGER -> {
                when(userTicketList.isEmpty()) {
                    true -> {
                        //TODO 21시 ~ 09시 예약 불가 메세지 처리
                    }
                    false -> {
                        TicketButtonMessage(message =  "중복 예약을 하실 수 없어요.")
                    }
                }
            }
            MemberRole.ADMIN -> {}
        }

        PrimaryButton(
            text = "예약하기",
            onClick = {
                if(userRole == MemberRole.PASSENGER){
                    coroutineScope.launch {
                        addNewPassengerToTicket(ticketDetail.id)
                        onCloseBottomSheet()
                        onRefresh(HomeBottomSheetViewModel.EVENT_ADDED_PASSENGER_TO_TICKET)
                    }
                }
            },
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
            enabled = userRole == MemberRole.PASSENGER && userTicketList.isEmpty()
        )
    }
}

@Composable
fun TicketHeader(
    startArea: String,
    endArea: String
) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            Modifier
                .width(120.dp)
                .fillMaxHeight()
                .border(1.dp, neutral30, RoundedCornerShape(5.dp))
                .padding(top = 8.dp, start = 12.dp, end = 12.dp, bottom = 8.dp)
                .weight(1f)
        ) {
            Text(
                text = "출발지",
                fontSize = 13.tu,
                fontWeight = FontWeight.Bold,
                color = neutral50
            )
            VerticalSpacer(height = 4.dp)
            Text(text = startArea, fontSize = 14.tu)
        }
        HorizontalSpacer(width = 12.dp)
        Image(
            painter = painterResource(id = R.drawable.ic_car_ticket),
            contentDescription = null,
            modifier = Modifier
                .height(24.dp)
                .width(72.dp)
        )
        HorizontalSpacer(width = 12.dp)
        Column(
            Modifier
                .width(120.dp)
                .fillMaxHeight()
                .border(1.dp, neutral30, RoundedCornerShape(5.dp))
                .padding(top = 8.dp, start = 12.dp, end = 12.dp, bottom = 8.dp)
                .weight(1f)
        ) {
            Text(
                text = "도착지",
                fontSize = 13.tu,
                fontWeight = FontWeight.Bold,
                color = neutral50
            )
            VerticalSpacer(height = 4.dp)
            Text(text = endArea, fontSize = 14.tu)
        }
    }
}

@Composable
private fun Driver(
    userProfile: String,
    memberName: String
){
    Row(
        Modifier
            .height(44.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileImage(
            userProfile,
            Modifier
                .width(50.dp)
                .height(47.dp)
                .clip(CircleShape)
                .border(1.dp, Color.White, CircleShape),
            defaultImage = R.drawable.ic_profile
        )
        Spacer(modifier = Modifier.width(9.dp))
        Column(
            modifier = Modifier
                .height(34.dp)
                .weight(1f)
        ) {
            Text(
                text = "드라이버",
                fontSize = 12.tu,
                color = neutral50,
                modifier = Modifier,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = memberName,
                fontSize = 14.tu,
                fontWeight = FontWeight.W400,
                modifier = Modifier
            )
        }
        Image(
            painter = painterResource(id = R.drawable.ic_navigate_next_small),
            contentDescription = null,
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
        )
    }
}

@Composable
fun TicketDetail(
    text1: String,
    text2: String,
    text3: String,
    text4: String
) {
    Row(
        Modifier
            .height(34.dp)
            .fillMaxWidth()
    )
    {
        Column(
            Modifier
                .weight(1f)
        )
        {
            Text(
                text = text1,
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                fontSize = 12.tu,
                fontWeight = FontWeight.Bold,
                color = neutral50
            )
            Text(
                text = text2,
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                fontSize = 14.tu,
                fontWeight = FontWeight.W400
            )
        }
        Column(
            Modifier
                .weight(1f)
        )
        {
            Text(
                text = text3,
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                fontSize = 12.tu,
                fontWeight = FontWeight.Bold,
                color = neutral50
            )
            Text(
                text = text4,
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                fontSize = 14.tu,
                fontWeight = FontWeight.W400
            )
        }
    }
}

@Composable
private fun TicketButtonMessage(message: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            fontSize = 12.tu,
            fontWeight = FontWeight.W400,
            color = primary60
        )
    }
    VerticalSpacer(height = 4.dp)
}

@Preview(showBackground = true)
@Composable
private fun PreviewBottomSheetContentDriver() =
    MateTheme {
        TicketBottomSheetContent(
            ticketDetail = TicketModel(
                id = 1,
                studentNumber = "",
                profileImage = "",
                memberName = "드라이버",
                startArea = "인동",
                endArea = "경운대학교",
                boardingPlace = "인동병원앞",
                dayStatus = DayStatus.AM,
                startTime = 25200L,
                openChatUrl = "link",
                recruitPerson = 3,
                ticketType = TicketType.Cost,
                ticketPrice = 20000,
                emptyList()
            ),
            addNewPassengerToTicket = {},
            userRole = MemberRole.DRIVER,
            userProfile = "",
            userTicketList = emptyList(),
            onRefresh = {},
            onCloseBottomSheet = {}
        )
    }

@Preview(showBackground = true)
@Composable
private fun PreviewBottomSheetContentPassengerReservedYet() =
    MateTheme {
        TicketBottomSheetContent(
            ticketDetail = TicketModel(
                id = 1,
                studentNumber = "",
                profileImage = "",
                memberName = "패신저",
                startArea = "인동",
                endArea = "경운대학교",
                boardingPlace = "인동사우나앞",
                dayStatus = DayStatus.AM,
                startTime = 25200L,
                openChatUrl = "link",
                recruitPerson = 5,
                ticketType = TicketType.Cost,
                ticketPrice = 5000,
                listOf(
                    UserModel.getInitValue(),
                    UserModel.getInitValue()
                )
            ),
            addNewPassengerToTicket = {},
            userRole = MemberRole.PASSENGER,
            userProfile = "",
            userTicketList = emptyList(),
            onRefresh = {},
            onCloseBottomSheet = {}
        )
    }

@Preview(showBackground = true)
@Composable
private fun PreviewBottomSheetContentPassengerReservedAlready() =
    MateTheme {
        TicketBottomSheetContent(
            ticketDetail = TicketModel(
                id = 1,
                studentNumber = "",
                profileImage = "",
                memberName = "패신저",
                startArea = "인동",
                endArea = "경운대학교",
                boardingPlace = "인동사우나앞",
                dayStatus = DayStatus.AM,
                startTime = 25200L,
                openChatUrl = "link",
                recruitPerson = 5,
                ticketType = TicketType.Cost,
                ticketPrice = 5000,
                listOf(
                    UserModel.getInitValue(),
                    UserModel.getInitValue()
                )
            ),
            addNewPassengerToTicket = {},
            userRole = MemberRole.PASSENGER,
            userProfile = "",
            userTicketList = listOf(TicketListModel.getInitValue()),
            onRefresh = {},
            onCloseBottomSheet = {}
        )
    }