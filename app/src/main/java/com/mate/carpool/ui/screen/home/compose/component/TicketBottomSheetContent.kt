package com.mate.carpool.ui.screen.home.compose.component

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
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.domain.UserModel
import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.data.model.item.MemberRole
import com.mate.carpool.data.model.item.TicketType
import com.mate.carpool.ui.composable.HorizontalSpacer
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.composable.button.PrimaryButton
import com.mate.carpool.ui.theme.*
import com.mate.carpool.ui.util.tu
import kotlinx.coroutines.launch

@Composable
fun BottomSheetContent(
    ticketDetail: TicketModel,
    userProfile: String,
    userRole: MemberRole,
    addNewPassengerToTicket: () -> Unit,
    onRefresh: () -> Unit,
    onCloseBottomSheet: suspend () -> Unit,
    emitSnackBarMessage: (String) -> Unit
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
            text2 = "${ticketDetail.startTime},${ticketDetail.startDayMonth}",
            text3 = "탑승 장소",
            text4 = ticketDetail.boardingPlace
        )
        VerticalSpacer(height = 20.dp)
        TicketDetail(
            text1 = "탑승 인원",
            text2 = ticketDetail.recruitPerson.toString() + "명",
            text3 = "비용",
            text4 = ticketDetail.ticketType?.displayName?:""
        )
        VerticalSpacer(height = 20.dp)

        PrimaryButton(
            text = "탑승하기",
            onClick = {
                if(userRole == MemberRole.Passenger){
                    coroutineScope.launch {
                        addNewPassengerToTicket()
                        onCloseBottomSheet()
                        onRefresh()
                    }
                }
                else
                    emitSnackBarMessage("드라이버는 탑승하기를 할 수 없습니다.")
            },
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
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
                .border(1.dp, Color.White, CircleShape)
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
                color = when (text4) {
                    "무료" -> primary50
                    "유료" -> red60
                    else -> Color.Black
                },
                fontSize = 14.tu,
                fontWeight = FontWeight.W400
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBottomSheetContent() =
    MateTheme {
        BottomSheetContent(
            ticketDetail = TicketModel(
                1,
                "",
                "",
                "황진호",
                "인동",
                "경운대학교",
                "경운대학교앞",
                "01/21",
                DayStatus.Morning,
                "08:00",
                "link",
                3,
                TicketType.Cost,
                20000,
                listOf(
                    UserModel(
                        "진호",
                        "20173000",
                        "동의대학교",
                        "010-1234-5678",
                        MemberRole.Driver,
                        "",
                        emptyList()
                    )
                )
            ),
            addNewPassengerToTicket = {},
            userRole = MemberRole.Driver,
            userProfile = "",
            onRefresh = {},
            onCloseBottomSheet = {},
            emitSnackBarMessage = {}
        )
    }