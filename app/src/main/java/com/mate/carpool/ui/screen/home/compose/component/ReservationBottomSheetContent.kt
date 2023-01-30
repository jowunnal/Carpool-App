package com.mate.carpool.ui.screen.home.compose.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.R
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.item.TicketStatus
import com.mate.carpool.data.model.domain.UserModel
import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.data.model.item.MemberRole
import com.mate.carpool.data.model.item.TicketType
import com.mate.carpool.ui.composable.HorizontalSpacer
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.composable.button.LargePrimaryButton
import com.mate.carpool.ui.composable.button.LargeSecondaryButton
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.theme.gray
import com.mate.carpool.ui.theme.neutral50
import com.mate.carpool.ui.util.tu
import com.mate.carpool.util.MatePreview
import kotlinx.coroutines.launch

@Composable
fun ReservationBottomSheetContent(
    ticketDetail: TicketModel,
    userProfile: String,
    userRole: MemberRole,
    userPassengerId: Long?,
    onCloseBottomSheet: suspend () -> Unit,
    onBrowseOpenChatLink: () -> Unit,
    onNavigateToReportView: () -> Unit,
    onRefresh: () -> Unit,
    setPassengerId: (Long) -> Unit,
    setStudentId: (String) -> Unit,
    deletePassengerFromTicket: () -> Unit,
    updateTicketStatus: (TicketStatus) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val popUpState = remember {
        mutableStateOf(false)
    }
    val popUpOffset = remember{
        mutableStateOf(Offset(0F,0F))
    }

    Column(
        modifier = Modifier
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            )
            .graphicsLayer(alpha = 0.95f)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        onCloseBottomSheet()
                        popUpState.value = false
                    }
                },
                modifier = Modifier
                    .size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_x),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    tint = gray
                )
            }
        }

        VerticalSpacer(height = 20.dp)

        TicketHeader(
            startArea = ticketDetail.startArea,
            endArea = ticketDetail.endArea
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

        TicketOpenChat(
            onBrowseOpenChatLink= onBrowseOpenChatLink
        )

        VerticalSpacer(height = 20.dp)

        Text(
            text = "드라이버",
            fontWeight = FontWeight.W700,
            fontSize = 12.tu,
            color = neutral50
        )
        MemberInfo(
            userProfile = ticketDetail.profileImage,
            memberName = ticketDetail.memberName,
            studentId = ticketDetail.studentNumber,
            iconDrawable = R.drawable.ic_navigate_next_small,
            onChangePopUpState = {popUpState.value = !popUpState.value},
            onSetPopUpOffset = { popUpOffset.value = it },
            setStudentId = setStudentId
        )
        VerticalSpacer(height = 20.dp)

        TicketPassengerList(
            memberRecruitNumber = ticketDetail.recruitPerson,
            memberGatherNumber = ticketDetail.passenger?.size?:0,
            passengerList = ticketDetail.passenger?: emptyList(),
            modifier = Modifier.weight(1f),
            onChangePopUpState = {popUpState.value = !popUpState.value},
            onSetPopUpOffset = { popUpOffset.value = it },
            setPassengerId = setPassengerId,
            setStudentId = setStudentId
        )

        TicketButton(
            userRole = userRole,
            userPassengerId = userPassengerId,
            setPassengerId = setPassengerId,
            updateTicketStatus = updateTicketStatus,
            deletePassengerFromTicket = deletePassengerFromTicket,
            onRefresh = onRefresh,
            onCloseBottomSheet = onCloseBottomSheet
        )

        PopupWindow(
            dialogState = popUpState.value,
            popUpOffset = popUpOffset.value,
            userRole = userRole,
            deletePassengerFromTicket = deletePassengerFromTicket,
            onNavigateToReportView = onNavigateToReportView,
            onRefresh = onRefresh
        )
    }
}

@Composable
private fun MemberInfo(
    userProfile: String,
    memberName: String,
    passengerId: Long = -1L,
    studentId: String,
    @DrawableRes iconDrawable: Int,
    onChangePopUpState: () -> Unit,
    onSetPopUpOffset: (Offset) -> Unit,
    setPassengerId: (Long) -> Unit = {},
    setStudentId: (String) -> Unit
){
    val clickState = remember {
        mutableStateOf(false)
    }

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
                text = memberName,
                fontSize = 14.tu,
                fontWeight = FontWeight.W400,
                modifier = Modifier
            )
        }
        IconButton(onClick = {
            onChangePopUpState()
            setPassengerId(passengerId)
            setStudentId(studentId)
            clickState.value = true
        }) {
            Icon(
                painter = painterResource(id = iconDrawable),
                contentDescription = null,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .onGloballyPositioned {
                        if (clickState.value) {
                            onSetPopUpOffset(
                                Offset(
                                    x = it.positionInWindow().x,
                                    y = it.positionInWindow().y
                                )
                            )
                            clickState.value = false
                        }
                    }
            )
        }
    }
}

@Composable
private fun TicketPassengerList(
    passengerList: List<UserModel>,
    memberRecruitNumber: Int,
    memberGatherNumber: Int,
    modifier: Modifier,
    onChangePopUpState: () -> Unit,
    onSetPopUpOffset: (Offset) -> Unit,
    setPassengerId: (Long) -> Unit,
    setStudentId: (String) -> Unit
) {
    LazyColumn(modifier = modifier) {
        item {
            Text(
                text = "패신저 ($memberGatherNumber/$memberRecruitNumber)",
                fontWeight = FontWeight.W700,
                fontSize = 12.tu,
                color = neutral50
            )
        }
        itemsIndexed(passengerList, key = {index,item -> item.passengerId}) {index, item ->
            MemberInfo(
                userProfile = item.profile,
                memberName = item.name,
                passengerId = item.passengerId,
                studentId = item.studentID,
                iconDrawable = R.drawable.ic_hamburger_circle,
                onChangePopUpState = onChangePopUpState,
                onSetPopUpOffset = onSetPopUpOffset,
                setPassengerId = setPassengerId,
                setStudentId = setStudentId
            )
        }
    }
}

@Composable
private fun TicketOpenChat(
    onBrowseOpenChatLink: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_kakao),
            contentDescription = null,
            modifier = Modifier
                .width(38.dp)
                .height(36.dp)
                .padding(8.dp),
            tint = Color.Unspecified
        )
        Text(
            text = "오픈카톡 참여하기",
            fontWeight = FontWeight.W400,
            fontSize = 14.tu,
            color = black,
            modifier = Modifier.weight(1f)
        )
        androidx.compose.material3.IconButton(
            onClick = onBrowseOpenChatLink
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_navigate_next_small),
                contentDescription = null,
                tint = gray
            )
        }
    }
}

@Composable
private fun TicketButton(
    userRole: MemberRole,
    userPassengerId: Long?,
    setPassengerId: (Long) -> Unit,
    updateTicketStatus: (TicketStatus) -> Unit,
    deletePassengerFromTicket: () -> Unit,
    onRefresh: () -> Unit,
    onCloseBottomSheet: suspend () -> Unit
){
    val coroutineScope = rememberCoroutineScope()
    Row() {
        when(userRole){
            MemberRole.Driver -> {
                LargeSecondaryButton(
                    text = "티켓 삭제",
                    onClick = { updateTicketStatus(TicketStatus.Cancel) },
                    modifier = Modifier.weight(1f)
                )
                HorizontalSpacer(width = 8.dp)
                LargePrimaryButton(
                    text = "운행 종료",
                    onClick = { updateTicketStatus(TicketStatus.After) },
                    modifier = Modifier.weight(1f)
                )
            }
            MemberRole.Passenger -> {
                LargePrimaryButton(
                    text = "예약 취소",
                    onClick = {
                        coroutineScope.launch {
                            setPassengerId(userPassengerId?:-1L)
                            deletePassengerFromTicket()
                            onCloseBottomSheet()
                            onRefresh()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewReservationBottomSheetContent() {
    MatePreview {
        ReservationBottomSheetContent(
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
                        "김민수",
                        "20173000",
                        "동의대학교",
                        "010-1234-5678",
                        MemberRole.Driver,
                        "",
                        emptyList(),
                        1
                    ),
                    UserModel(
                        "이수영",
                        "20173001",
                        "동의대학교",
                        "010-1234-5678",
                        MemberRole.Driver,
                        "",
                        emptyList(),
                        2
                    )
                )
            ),
            userProfile = "",
            userRole = MemberRole.Passenger,
            onBrowseOpenChatLink = {},
            onRefresh = {},
            deletePassengerFromTicket = {},
            updateTicketStatus = {},
            setPassengerId = {},
            onNavigateToReportView = {},
            userPassengerId = 0L,
            onCloseBottomSheet = {},
            setStudentId = {}
        )
    }
}