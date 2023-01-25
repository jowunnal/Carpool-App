package com.mate.carpool.ui.screen.home.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.mate.carpool.R
import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.item.MemberRole
import com.mate.carpool.ui.base.BaseBottomSheetDialogFragment
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.navigation.NavigationGraph
import com.mate.carpool.ui.screen.home.compose.component.*
import com.mate.carpool.ui.screen.home.vm.CarpoolListViewModelInterface
import com.mate.carpool.ui.screen.home.vm.HomeBottomSheetViewModelInterface
import com.mate.carpool.ui.screen.home.vm.PreviewCarpoolListViewModel
import com.mate.carpool.ui.screen.home.vm.PreviewHomeBottomSheetViewModel
import com.mate.carpool.ui.screen.reserveDriver.fragment.ReserveDriverFragment
import com.mate.carpool.ui.screen.reservePassenger.ReservePassengerFragment
import com.mate.carpool.ui.theme.MateTheme
import com.mate.carpool.ui.theme.primary50
import com.mate.carpool.ui.utils.IntUtils.toSp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


@Composable
fun MainView(
    onNavigateToCreateCarpool: () -> Unit,
    onNavigateToProfileView: () -> Unit
) {
    val navController = rememberNavController()
    NavigationGraph(
        navController = navController,
        onNavigateToCreateCarpool,
        onNavigateToProfileView
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeBottomSheetLayout(
    fragmentManager: FragmentManager,
    onNavigateToCreateCarpool: () -> Unit,
    onNavigateToProfileView: () -> Unit,
    homeCarpoolBottomSheetViewModel: HomeBottomSheetViewModelInterface,
    carpoolListViewModel: CarpoolListViewModelInterface
) {
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val coroutineScope = rememberCoroutineScope()

    val ticketId = homeCarpoolBottomSheetViewModel.mutableTicketId
    val bottomSheetMemberModel = homeCarpoolBottomSheetViewModel.memberModel
    val initViewState = homeCarpoolBottomSheetViewModel.initViewState

    val reNewHomeListener = object : BaseBottomSheetDialogFragment.Renewing() {
        override fun onRewNew() {
            initViewState.value = true
        }
    }

    LaunchedEffect(key1 = bottomSheetState.currentValue){
        if(bottomSheetState.currentValue == ModalBottomSheetValue.Hidden){
            initViewState.value=true
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            HomeBottomSheetContent(
                bottomSheetState = bottomSheetState,
                bottomSheetMemberModel = bottomSheetMemberModel,
                homeCarpoolBottomSheetViewModel = homeCarpoolBottomSheetViewModel,
                reNewHomeListener = reNewHomeListener,
                fragmentManager = fragmentManager
            )
        },
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(20.dp)
    ) {
        HomeView(
            bottomSheetState = bottomSheetState,
            coroutineScope = coroutineScope,
            onNavigateToCreateCarpool = onNavigateToCreateCarpool,
            onNavigateToProfileView = onNavigateToProfileView,
            bottomSheetMemberModel = bottomSheetMemberModel,
            ticketId = ticketId,
            reNewHomeListener = reNewHomeListener,
            initViewState = initViewState,
            fragmentManager = fragmentManager,
            carpoolListViewModel = carpoolListViewModel
        )
    }
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeView(
    bottomSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    onNavigateToCreateCarpool: () -> Unit,
    onNavigateToProfileView: () -> Unit,
    bottomSheetMemberModel: MutableStateFlow<MemberModel>,
    ticketId: MutableStateFlow<Long>,
    reNewHomeListener: BaseBottomSheetDialogFragment.Renewing,
    initViewState: MutableState<Boolean>,
    fragmentManager: FragmentManager,
    carpoolListViewModel: CarpoolListViewModelInterface
) {
    val carpoolExistState by carpoolListViewModel.carpoolExistState.collectAsStateWithLifecycle()
    val memberModel by carpoolListViewModel.memberModelState.collectAsStateWithLifecycle()
    val isRefreshing = carpoolListViewModel.isRefreshState


    LaunchedEffect(key1 = initViewState.value) {
        if (initViewState.value) {
            carpoolListViewModel.getMemberModel()
            carpoolListViewModel.getCarpoolList()
            initViewState.value = false
            isRefreshing.value = false
        }
    }


    Column {
        HomeAppBar(
            profileImage = memberModel.user.profile,
            goToProfileScreen = onNavigateToProfileView
        )
        Column(Modifier.padding(start = 16.dp, end = 16.dp, top = 32.dp)) {
            HomeCardView(R.drawable.ic_home_folder, "공지사항", R.drawable.ic_home_rightarrow, {})
            Spacer(modifier = Modifier.height(4.dp))

            when (memberModel.user.role) {
                MemberRole.Passenger -> {
                    //HomeCardView(R.drawable.ic_home_location,"지역설정",R.drawable.ic_home_rightarrow,{})
                }

                MemberRole.Driver -> {
                    HomeCardView(
                        R.drawable.ic_car_blue,
                        "카풀 모집하기",
                        R.drawable.ic_home_rightarrow,
                        onNavigateToCreateCarpool
                    )
                }
            }

            VerticalSpacer(height = 4.dp)

            Column(
                Modifier
                    .weight(1f)
            ) {
                HomeCarpoolList(
                    bottomSheetState = bottomSheetState,
                    memberModel = memberModel,
                    bottomSheetMemberModel = bottomSheetMemberModel,
                    ticketId = ticketId,
                    reNewHomeListener = reNewHomeListener,
                    initViewState = initViewState,
                    isRefreshing = isRefreshing,
                    fragmentManager = fragmentManager,
                    carpoolListViewModel = carpoolListViewModel
                )
            }

            VerticalSpacer(height = 50.dp)

            Button(
                onClick = {
                    coroutineScope.launch {
                        if (carpoolExistState) {
                            when (memberModel.user.role) {
                                MemberRole.Passenger -> {
                                    ReservePassengerFragment(
                                        memberModel.user.studentID,
                                        reNewHomeListener
                                    ).show(fragmentManager, "passenger reservation")
                                }

                                MemberRole.Driver -> {
                                    ReserveDriverFragment(
                                        reNewHomeListener
                                    ).show(fragmentManager, "driver reservation")
                                }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .background(Color.Unspecified),
                colors =
                if (!carpoolExistState)
                    ButtonDefaults.buttonColors(Color.Black)
                else
                    ButtonDefaults.buttonColors(primary50),
                shape = RoundedCornerShape(100.dp)
            ) {
                Text(
                    text =
                    if (!carpoolExistState) {
                        when (memberModel.user.role) {
                            MemberRole.Passenger -> "예약된 카풀이 없습니다."
                            MemberRole.Driver -> "생성한 카풀이 없습니다."
                        }
                    } else
                        "내 카풀 보기",
                    fontSize = 18.toSp(),
                    fontWeight = FontWeight.W900,
                    color = Color.White
                )
            }

            VerticalSpacer(height = 22.dp)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    MateTheme() {
        HomeBottomSheetLayout(
            onNavigateToCreateCarpool = {},
            onNavigateToProfileView = {},
            homeCarpoolBottomSheetViewModel = PreviewHomeBottomSheetViewModel,
            fragmentManager = object : FragmentManager() {

            },
            carpoolListViewModel = PreviewCarpoolListViewModel
        )
    }
}