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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.data.model.item.*
import com.mate.carpool.ui.composable.HorizontalDivider
import com.mate.carpool.ui.composable.HorizontalDividerItem
import com.mate.carpool.ui.composable.HorizontalSpacer
import com.mate.carpool.ui.screen.home.item.TicketListState
import com.mate.carpool.ui.screen.home.vm.HomeBottomSheetViewModel
import com.mate.carpool.ui.theme.*
import com.mate.carpool.ui.util.appendBoldText
import com.mate.carpool.ui.util.tu
import com.mate.carpool.util.formatStartTimeToDTO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TicketList(
    refreshState: Boolean,
    carpoolList: List<TicketListState>,
    getTicketDetail: (String) -> Unit,
    onRefresh: (String) -> Unit,
    onOpenBottomSheet: suspend () -> Unit,
    isTicketIsMineOrNot: (String, List<TicketListState>) -> Unit,
    userTicketList: List<TicketListState>
) {
    val coroutineScope = rememberCoroutineScope()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshState,
        onRefresh = { onRefresh(HomeBottomSheetViewModel.EVENT_ADDED_PASSENGER_TO_TICKET) }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            if (!refreshState) {
                HorizontalDividerItem()
                itemsIndexed(items = carpoolList, key = { _, item -> item.id }) { index, item ->
                    Column(modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            coroutineScope.launch {
                                getTicketDetail(item.id)
                                isTicketIsMineOrNot(item.id, userTicketList)
                                delay(50)
                                onOpenBottomSheet()
                            }
                        }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically)
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
                                    text = buildAnnotatedString {
                                        appendBoldText(item.startArea)
                                        append(" 출발,${item.dayStatus.displayName}")
                                        appendBoldText(" ${item.startTime.formatStartTimeToDTO()}")
                                    },
                                    fontSize = 16.tu
                                )
                            }

                            com.mate.carpool.ui.composable.Badge(
                                maximumNumber = item.recruitPerson,
                                currentNumber = item.currentPersonCount,
                                status = item.ticketStatus
                            )
                        }
                    }
                    if (index != carpoolList.lastIndex)
                        HorizontalDivider()
                }
                HorizontalDividerItem()
            }
        }
        PullRefreshIndicator(
            refreshing = refreshState,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTicketList() {
    MateTheme {
        TicketList(
            refreshState = false,
            carpoolList =
            listOf(
                TicketListState(
                    id = "1",
                    profileImage = "",
                    startArea = "인동",
                    startTime = 25200L,
                    recruitPerson = 3,
                    currentPersonCount = 1,
                    ticketStatus = TicketStatus.Before,
                    dayStatus = DayStatus.AM
                ),
                TicketListState(
                    id = "2",
                    profileImage = "",
                    startArea = "인동",
                    startTime = 25200L,
                    recruitPerson = 3,
                    currentPersonCount = 1,
                    ticketStatus = TicketStatus.Before,
                    dayStatus = DayStatus.AM
                ),
                TicketListState(
                    id = "3",
                    profileImage = "",
                    startArea = "인동",
                    startTime = 25200L,
                    recruitPerson = 3,
                    currentPersonCount = 1,
                    ticketStatus = TicketStatus.Before,
                    dayStatus = DayStatus.AM
                ),
                TicketListState(
                    id = "4",
                    profileImage = "",
                    startArea = "인동",
                    startTime = 25200L,
                    recruitPerson = 3,
                    currentPersonCount = 1,
                    ticketStatus = TicketStatus.Before,
                    dayStatus = DayStatus.AM
                )
            ),
            onRefresh = {},
            onOpenBottomSheet = {},
            getTicketDetail = {},
            userTicketList = emptyList(),
            isTicketIsMineOrNot = { ticketId, ticketListStates -> }
        )
    }
}

