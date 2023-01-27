package com.mate.carpool.ui.screen.home.compose.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.domain.item.*
import com.mate.carpool.ui.composable.HorizontalDivider
import com.mate.carpool.ui.composable.HorizontalDividerItem
import com.mate.carpool.ui.composable.HorizontalSpacer
import com.mate.carpool.ui.screen.reserveDriver.fragment.ReserveDriverFragment
import com.mate.carpool.ui.screen.reservePassenger.ReservePassengerFragment
import com.mate.carpool.ui.theme.*
import com.mate.carpool.ui.util.tu
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TicketList(
    bottomSheetState: ModalBottomSheetState,
    userRole: MemberRole,
    userStudentID: String,
    initViewState: MutableState<Boolean>,
    fragmentManager: FragmentManager,
    carpoolList : List<TicketListModel>,
    reNewHomeListener: () -> Unit,
    setTicketId: (Long) -> Unit,
    isTicketMineOrNot : (Long) -> Boolean
){
    val coroutineScope = rememberCoroutineScope()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = initViewState.value,
        onRefresh = {
            initViewState.value = true
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            HorizontalDividerItem()

            itemsIndexed(items = carpoolList, key = { _, item -> item.id }) { index, item ->
                Column(modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        if (!bottomSheetState.isVisible) {
                            coroutineScope.launch {
                                when (userRole) {
                                    MemberRole.Driver -> {
                                        if (isTicketMineOrNot(item.id)) {
                                            ReserveDriverFragment(
                                                reNewHomeListener
                                            ).show(fragmentManager, "driver reservation")
                                        } else {
                                            showBottomSheet(
                                                setTicketId = setTicketId,
                                                itemId = item.id,
                                                bottomSheetState = bottomSheetState
                                            )
                                        }
                                    }
                                    MemberRole.Passenger -> {
                                        if (isTicketMineOrNot(item.id)) {
                                            ReservePassengerFragment(
                                                userStudentID,
                                                reNewHomeListener
                                            ).show(fragmentManager, "passenger reservation")
                                        } else {
                                            showBottomSheet(
                                                setTicketId = setTicketId,
                                                itemId = item.id,
                                                bottomSheetState = bottomSheetState
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                ) {
                    Row ( verticalAlignment = Alignment.CenterVertically )
                    {
                        ProfileImage(
                            profileImage = item.profileImage,
                            modifier = Modifier
                                .width(50.dp)
                                .height(47.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color.White, CircleShape)
                        )

                        HorizontalSpacer(width = 8.dp)

                        Row(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.startArea,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 16.tu
                            )
                            Text(
                                text = " 출발,",
                                fontSize = 16.tu
                            )
                            Text(
                                text = item.dayStatus?.getDayStatus() ?: "",
                                fontSize = 16.tu
                            )
                            Text(
                                text = " ${item.startTime}",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 16.tu
                            )
                        }

                        Chip(
                            onClick = { /*TODO*/ },
                            colors = when (item.ticketType) {
                                TicketType.Free -> ChipDefaults.chipColors(primary50)
                                TicketType.Cost -> ChipDefaults.chipColors(red50)
                                else -> ChipDefaults.chipColors(neutral50)
                            }
                        )
                        {
                            Text(text = "${item.currentPersonCount}/${item.recruitPerson}")
                        }
                    }
                }
                if(index != carpoolList.lastIndex)
                    HorizontalDivider()
            }
            HorizontalDividerItem()
        }
        PullRefreshIndicator(
            refreshing = initViewState.value,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
private suspend fun showBottomSheet(
    setTicketId: (Long) -> Unit,
    itemId: Long,
    bottomSheetState: ModalBottomSheetState
) {
    setTicketId(itemId)
    bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PreviewTicketList() {
    MateTheme {
        TicketList(
            bottomSheetState = ModalBottomSheetState(ModalBottomSheetValue.Expanded),
            userRole = MemberRole.Driver,
            userStudentID = "",
            setTicketId = {},
            reNewHomeListener = {},
            initViewState = remember{ mutableStateOf(false) },
            fragmentManager = object : FragmentManager(){},
            isTicketMineOrNot = {false},
            carpoolList =
            listOf(
                TicketListModel(
                    1,
                    "",
                    "인동",
                    "08:00",
                    3,
                    1,
                    TicketType.Cost,
                    TicketStatus.Before,
                    DayStatus.Morning
                ),
                TicketListModel(
                    2,
                    "",
                    "인동",
                    "07:00",
                    3,
                    1,
                    TicketType.Cost,
                    TicketStatus.Before,
                    DayStatus.Morning
                ),
                TicketListModel(
                    3,
                    "",
                    "인동",
                    "08:00",
                    3,
                    1,
                    TicketType.Free,
                    TicketStatus.Before,
                    DayStatus.Morning
                ),
                TicketListModel(
                    4,
                    "",
                    "인동",
                    "09:00",
                    3,
                    1,
                    TicketType.Free,
                    TicketStatus.Before,
                    DayStatus.Morning
                )
            )
        )
    }
}

