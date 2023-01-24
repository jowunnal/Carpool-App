package com.mate.carpool.ui.screen.home.compose.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mate.carpool.R
import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.item.MemberRole
import com.mate.carpool.data.model.domain.item.TicketType
import com.mate.carpool.data.model.domain.item.getDayStatus
import com.mate.carpool.ui.base.BaseBottomSheetDialogFragment
import com.mate.carpool.ui.screen.home.vm.CarpoolListViewModelInterface
import com.mate.carpool.ui.screen.home.vm.PreviewCarpoolListViewModel
import com.mate.carpool.ui.screen.home.vm.PreviewHomeBottomSheetViewModel
import com.mate.carpool.ui.screen.reserveDriver.fragment.ReserveDriverFragment
import com.mate.carpool.ui.screen.reservePassenger.ReservePassengerFragment
import com.mate.carpool.ui.theme.neutral20
import com.mate.carpool.ui.theme.neutral50
import com.mate.carpool.ui.theme.primary50
import com.mate.carpool.ui.theme.red50
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeCarpoolList(
    bottomSheetState: ModalBottomSheetState,
    memberModel: MemberModel,
    bottomSheetMemberModel: MutableStateFlow<MemberModel>,
    ticketId: MutableStateFlow<Long>,
    reNewHomeListener: BaseBottomSheetDialogFragment.Renewing,
    initViewState: MutableState<Boolean>,
    isRefreshing: MutableState<Boolean>,
    fragmentManager: FragmentManager,
    carpoolListViewModel: CarpoolListViewModelInterface
){
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(44.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_home_list),
                contentDescription = null,
                Modifier
                    .width(24.dp)
                    .height(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "카풀 목록",
                modifier = Modifier.weight(1f)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_home_reddot),
                contentDescription = null
            )
            Text(text = "유료")
            Spacer(modifier = Modifier.width(6.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_home_bluedot),
                contentDescription = null
            )
            Text(text = "무료")
        }
        HomeCarpoolItems(
            bottomSheetState,
            memberModel,
            bottomSheetMemberModel,
            ticketId,
            reNewHomeListener,
            initViewState,
            isRefreshing,
            fragmentManager,
            carpoolListViewModel
        )
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeCarpoolItems(
    bottomSheetState: ModalBottomSheetState,
    memberModel: MemberModel,
    bottomSheetMemberModel: MutableStateFlow<MemberModel>,
    ticketId: MutableStateFlow<Long>,
    reNewHomeListener: BaseBottomSheetDialogFragment.Renewing,
    initViewState: MutableState<Boolean>,
    isRefreshing: MutableState<Boolean>,
    fragmentManager: FragmentManager,
    carpoolListViewModel: CarpoolListViewModelInterface
){
    val carpoolList by carpoolListViewModel.carpoolListState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing.value,
        onRefresh = {
            isRefreshing.value = true
            initViewState.value = true
        }
    )

    Box(
        modifier = Modifier
            .shadow(1.dp)
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            items(items = carpoolList, key = { item -> item.id }) { item ->
                Column(Modifier.clickable {
                    if (!bottomSheetState.isVisible) {
                        coroutineScope.launch {
                            when(memberModel.user.role){
                                MemberRole.Driver->{
                                    if(carpoolListViewModel.isTicketIsMineOrNot(item.id)){
                                        ReserveDriverFragment(
                                            reNewHomeListener
                                        ).show(fragmentManager, "driver reservation")
                                    } else {
                                        showBottomSheet(
                                            ticketId,
                                            item.id,
                                            bottomSheetMemberModel,
                                            memberModel,
                                            bottomSheetState
                                        )
                                    }
                                }
                                MemberRole.Passenger->{
                                    if(carpoolListViewModel.isTicketIsMineOrNot(item.id)){
                                        ReservePassengerFragment(
                                            memberModel.user.studentID,
                                            reNewHomeListener
                                        ).show(fragmentManager, "passenger reservation")
                                    } else {
                                        showBottomSheet(
                                            ticketId,
                                            item.id,
                                            bottomSheetMemberModel,
                                            memberModel,
                                            bottomSheetState
                                        )
                                    }
                                }
                            }

                        }
                    }
                }) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        ProfileImage(
                            profileImage = item.profileImage,
                            modifier = Modifier
                                .width(50.dp)
                                .height(47.dp)
                                .padding(3.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color.White, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Row(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.startArea,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = " 출발,"
                            )
                            Text(
                                text = item.dayStatus?.getDayStatus() ?: ""
                            )
                            Text(
                                text = item.startTime,
                                fontWeight = FontWeight.Bold
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
                Text(
                    text = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(neutral20)
                )
            }
        }
        PullRefreshIndicator(
            refreshing = isRefreshing.value,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
private suspend fun showBottomSheet(
    ticketId: MutableStateFlow<Long>,
    itemId: Long,
    bottomSheetMemberModel: MutableStateFlow<MemberModel>,
    memberModel: MemberModel,
    bottomSheetState: ModalBottomSheetState
) {
    ticketId.value = itemId
    bottomSheetMemberModel.value.user.role = memberModel.user.role
    bottomSheetMemberModel.value.user.studentID = memberModel.user.studentID
    bottomSheetMemberModel.value.user.profile = memberModel.user.profile
    bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
}

@SuppressLint("StateFlowValueCalledInComposition", "UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun PreviewHomeCarpoolItems(){
    HomeCarpoolItems(
        bottomSheetState = ModalBottomSheetState(ModalBottomSheetValue.Expanded),
        memberModel = PreviewHomeBottomSheetViewModel.memberModel.value,
        bottomSheetMemberModel = PreviewHomeBottomSheetViewModel.memberModel,
        ticketId = MutableStateFlow(0L),
        reNewHomeListener = object : BaseBottomSheetDialogFragment.Renewing(){ override fun onRewNew() { } },
        initViewState = mutableStateOf(false),
        isRefreshing = mutableStateOf(false),
        fragmentManager = object : FragmentManager(){},
        carpoolListViewModel = PreviewCarpoolListViewModel
    )
}
