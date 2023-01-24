package com.mate.carpool.ui.screen.home.compose

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.mate.carpool.R
import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.item.MemberRole
import com.mate.carpool.data.model.domain.item.TicketType
import com.mate.carpool.data.model.domain.item.getDayStatus
import com.mate.carpool.data.model.domain.item.getTicketType
import com.mate.carpool.ui.base.BaseBottomSheetDialogFragment
import com.mate.carpool.ui.navigation.NavigationGraph
import com.mate.carpool.ui.screen.home.vm.*
import com.mate.carpool.ui.screen.reserveDriver.fragment.ReserveDriverFragment
import com.mate.carpool.ui.screen.reservePassenger.ReservePassengerFragment
import com.mate.carpool.ui.theme.Colors
import com.mate.carpool.ui.utils.IntUtils.toSp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                HomeMainView(
                    onNavigateToCreateCarpool = {findNavController().navigate(R.id.action_homeFragment_to_createTicketBoardingAreaFragment)}
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview(){
    HomeBottomSheetLayout(
        onNavigateToCreateCarpool = { /*TODO*/ },
        homeCarpoolBottomSheetViewModel = PreviewHomeBottomSheetViewModel,
        fragmentManager = object:FragmentManager(){

        },
        carpoolListViewModel = PreviewCarpoolListViewModel
    )
}

@Composable
fun HomeMainView(
    onNavigateToCreateCarpool: () -> Unit
){
    val navController = rememberNavController()
    NavigationGraph(
        navController = navController,
        onNavigateToCreateCarpool
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeBottomSheetLayout(
    fragmentManager: FragmentManager,
    onNavigateToCreateCarpool: ()->Unit,
    homeCarpoolBottomSheetViewModel: HomeBottomSheetViewModelInterface,
    carpoolListViewModel: CarpoolListViewModelInterface
) {
    val bottomSheetState = rememberModalBottomSheetState (
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
                coroutineScope = coroutineScope,
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
            bottomSheetMemberModel = bottomSheetMemberModel,
            ticketId = ticketId,
            reNewHomeListener = reNewHomeListener,
            initViewState = initViewState,
            fragmentManager = fragmentManager,
            carpoolListViewModel = carpoolListViewModel
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeBottomSheetContent(
    bottomSheetState: ModalBottomSheetState,
    bottomSheetMemberModel: MutableStateFlow<MemberModel>,
    coroutineScope: CoroutineScope,
    homeCarpoolBottomSheetViewModel: HomeBottomSheetViewModelInterface,
    reNewHomeListener: BaseBottomSheetDialogFragment.Renewing,
    fragmentManager: FragmentManager
){
    val ticketDetail by homeCarpoolBottomSheetViewModel.carpoolTicketState.collectAsStateWithLifecycle()
    val newPassengerState by homeCarpoolBottomSheetViewModel.newPassengerState.collectAsStateWithLifecycle()
    val toastMessage = homeCarpoolBottomSheetViewModel.toastMessage.collectAsStateWithLifecycle()
    val context = LocalContext.current

    if(toastMessage.value != ""){
        LaunchedEffect(key1 = toastMessage.value){
            Toast.makeText(context,toastMessage.value, Toast.LENGTH_SHORT).show()
        }
    }

    if(newPassengerState){
        LaunchedEffect(key1 = newPassengerState){
            ReservePassengerFragment(
                bottomSheetMemberModel.value.user.studentID,
                reNewHomeListener
            ).show(fragmentManager,"passenger reservation")
            bottomSheetState.animateTo(ModalBottomSheetValue.Hidden)
            homeCarpoolBottomSheetViewModel.initNewPassengerState()
        }
    }

    Column(
        Modifier
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 16.dp,
                bottom = 20.dp
            )
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = {
                coroutineScope.launch {
                    bottomSheetState.hide()
                }
            })
            {
                Icon(
                    painter = painterResource(id = R.drawable.icon_x),
                    contentDescription = null,
                    modifier = Modifier
                        .height(14.dp)
                        .width(14.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            Modifier
                .height(60.dp)
                .fillMaxWidth()
        ) {
            Column(
                Modifier
                    .width(120.dp)
                    .fillMaxHeight()
                    .border(1.dp, Colors.Gray_A2ABB4, RoundedCornerShape(5.dp))
                    .padding(top = 8.dp, start = 12.dp, end = 12.dp, bottom = 8.dp)
            )
            {
                Text(text = "출발지", fontSize = 13.toSp(), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = ticketDetail.startArea, fontSize = 18.toSp())
            }
            Spacer(modifier = Modifier.width(5.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_ticket_bluearrow),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column(
                Modifier
                    .width(120.dp)
                    .fillMaxHeight()
                    .border(1.dp, Colors.Gray_A2ABB4, RoundedCornerShape(5.dp))
                    .padding(top = 8.dp, start = 12.dp, end = 12.dp, bottom = 8.dp)
            ) {
                Text(text = "도착지", fontSize = 13.toSp(), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = ticketDetail.endArea, fontSize = 18.toSp())
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            Modifier
                .height(44.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            ProfileImage(
                bottomSheetMemberModel.value.user.profile,
                Modifier
                    .width(50.dp)
                    .height(47.dp)
                    .padding(3.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.White, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                Modifier
                    .weight(1f)
                    .height(34.dp)) {
                Text(
                    text = "드라이버",
                    fontSize = 12.toSp(),
                    color = Colors.Gray_4E5760,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = ticketDetail.memberName,
                    fontSize = 14.toSp(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_home_rightarrow),
                contentDescription = null,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        HomeTicketDetail(
            text1 = "출발 시간",
            text2 = "${ticketDetail.startTime},${ticketDetail.startDayMonth}",
            text3 = "탑승 장소",
            text4 = ticketDetail.boardingPlace
        )
        Spacer(modifier = Modifier.height(20.dp))
        HomeTicketDetail(
            text1 = "탑승 인원",
            text2 = ticketDetail.recruitPerson.toString() + "명",
            text3 = "비용",
            text4 = ticketDetail.ticketType?.getTicketType()?:""
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                if(bottomSheetMemberModel.value.user.role == MemberRole.Passenger){
                    homeCarpoolBottomSheetViewModel.addNewPassengerToTicket(ticketDetail.id)
                }
                else
                    Toast.makeText(context,"드라이버는 탑승하기를 할 수 없습니다",Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(Colors.Blue_007AFF),
            shape = RoundedCornerShape(100.dp)
        )
        {
            Text(
                text = "탑승하기",
                color = Color.White,
                fontWeight = FontWeight.W900,
                fontSize = 18.toSp()
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeView(
    bottomSheetState:ModalBottomSheetState,
    coroutineScope:CoroutineScope,
    onNavigateToCreateCarpool:()->Unit,
    bottomSheetMemberModel:MutableStateFlow<MemberModel>,
    ticketId:MutableStateFlow<Long>,
    reNewHomeListener:BaseBottomSheetDialogFragment.Renewing,
    initViewState:MutableState<Boolean>,
    fragmentManager: FragmentManager,
    carpoolListViewModel: CarpoolListViewModelInterface
){
    val carpoolExistState by carpoolListViewModel.carpoolExistState.collectAsStateWithLifecycle()
    val memberModel by carpoolListViewModel.memberModelState.collectAsStateWithLifecycle()
    val isRefreshing = carpoolListViewModel.isRefreshState

    LaunchedEffect(key1 = initViewState.value){
        if(initViewState.value){
            carpoolListViewModel.getMemberModel()
            carpoolListViewModel.getCarpoolList()
            initViewState.value = false
            isRefreshing.value = false
        }
    }

    Column() {

        HomeAppBar(memberModel.user.profile)

        Column(Modifier.padding(start = 16.dp, end = 16.dp,top=32.dp)) {
            HomeCardView(R.drawable.ic_home_folder,"공지사항",R.drawable.ic_home_rightarrow,{})
            Spacer(modifier = Modifier.height(4.dp))

            when(memberModel.user.role){
                MemberRole.Passenger -> {
                    //HomeCardView(R.drawable.ic_home_location,"지역설정",R.drawable.ic_home_rightarrow,{})
                }
                MemberRole.Driver -> {
                    HomeCardView(R.drawable.ic_car_blue,"카풀 모집하기",R.drawable.ic_home_rightarrow,onNavigateToCreateCarpool)
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Column(Modifier
                .weight(1f)) {
                HomeCarpoolList(
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
            Spacer(modifier = Modifier.height(50.dp))
            Button(onClick = {
                coroutineScope.launch {
                    if(carpoolExistState) {
                        when(memberModel.user.role){
                            MemberRole.Passenger->{
                                ReservePassengerFragment(
                                    memberModel.user.studentID,
                                    reNewHomeListener
                                ).show(fragmentManager,"passenger reservation")
                            }
                            MemberRole.Driver->{
                                ReserveDriverFragment(
                                    reNewHomeListener
                                ).show(fragmentManager,"driver reservation")
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
                    if(!carpoolExistState)
                        ButtonDefaults.buttonColors(Color.Black)
                    else
                        ButtonDefaults.buttonColors(Colors.Blue_007AFF),
                shape = RoundedCornerShape(100.dp)
            ) {
                Text(
                    text =
                        if(!carpoolExistState) {
                            when(memberModel.user.role){
                                MemberRole.Passenger -> "예약된 카풀이 없습니다."
                                MemberRole.Driver -> "생성한 카풀이 없습니다."
                            }
                        }
                        else
                            "내 카풀 보기",
                    fontSize = 18.toSp(),
                    fontWeight = FontWeight.W900,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(22.dp))
        }
    }

}

@Composable
fun HomeCardView(
    @DrawableRes imageId:Int,
    text:String,
    @DrawableRes icon:Int,
    onNavigateCallBack:()->Unit
){
    ElevatedCard(shape = RoundedCornerShape(7.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
    colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(4.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = null,
            modifier = Modifier
                .width(20.dp)
                .height(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text,
                fontSize = 16.toSp(),
                fontWeight = FontWeight.Bold,
                color=Color.Black
                ,modifier = Modifier
                    .weight(1f)
                    .height(22.dp))

            IconButton(onClick = onNavigateCallBack) {
                Icon(painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeCarpoolList(
    bottomSheetState:ModalBottomSheetState,
    memberModel: MemberModel,
    bottomSheetMemberModel:MutableStateFlow<MemberModel>,
    ticketId:MutableStateFlow<Long>,
    reNewHomeListener:BaseBottomSheetDialogFragment.Renewing,
    initViewState:MutableState<Boolean>,
    isRefreshing:MutableState<Boolean>,
    fragmentManager: FragmentManager,
    carpoolListViewModel: CarpoolListViewModelInterface
){
    Column() {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(44.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.ic_home_list),
                contentDescription = null,
                Modifier
                    .width(24.dp)
                    .height(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "카풀 목록",
                modifier = Modifier.weight(1f))
            Image(painter = painterResource(id = R.drawable.ic_home_reddot),
                contentDescription = null)
            Text(text = "유료")
            Spacer(modifier = Modifier.width(6.dp))
            Image(painter = painterResource(id = R.drawable.ic_home_bluedot),
                contentDescription = null)
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

@OptIn( ExperimentalLifecycleComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeCarpoolItems(
    bottomSheetState: ModalBottomSheetState,
    memberModel: MemberModel,
    bottomSheetMemberModel:MutableStateFlow<MemberModel>,
    ticketId:MutableStateFlow<Long>,
    reNewHomeListener:BaseBottomSheetDialogFragment.Renewing,
    initViewState:MutableState<Boolean>,
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

    Box(modifier = Modifier
        .shadow(1.dp)
        .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            items(carpoolList,key = {item -> item.id}) { item ->
                Column(Modifier.clickable {
                    if(!bottomSheetState.isVisible){
                        coroutineScope.launch {
                            when(memberModel.user.role){
                                MemberRole.Driver->{
                                    if(carpoolListViewModel.isTicketIsMineOrNot(item.id)){
                                        ReserveDriverFragment(
                                            reNewHomeListener
                                        ).show(fragmentManager,"driver reservation")
                                    }
                                    else{
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
                                        ).show(fragmentManager,"passenger reservation")
                                    }
                                    else {
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
                                text = item.dayStatus?.getDayStatus()?:""
                            )
                            Text(
                                text = item.startTime,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Chip(
                            onClick = { /*TODO*/ },
                            colors = when (item.ticketType) {
                                TicketType.Free -> ChipDefaults.chipColors(Colors.Blue_007AFF)
                                TicketType.Cost -> ChipDefaults.chipColors(Colors.Red_E0302D)
                                else -> ChipDefaults.chipColors(Colors.Gray_4E5760)
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
                        .background(Colors.Gray_DADDE1)
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

@Composable
fun HomeTicketDetail(
    text1:String,
    text2:String,
    text3:String,
    text4:String
){
    Row(
        Modifier
            .height(34.dp)
            .fillMaxWidth())
    {
        Column(Modifier
            .weight(1f))
        {
            Text(text = text1,
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                fontSize = 12.toSp(),
                fontWeight = FontWeight.Bold
            )
            Text(text = text2,
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                fontSize = 14.toSp()
            )
        }
        Column(Modifier
            .weight(1f))
        {
            Text(text = text3,
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                fontSize = 12.toSp(),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = text4,
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = when(text4){
                    "무료" -> Colors.Blue_007AFF
                    "유료" -> Colors.Red_E0302D
                    else -> Color.Black
                },
                fontSize = 14.toSp()
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
private suspend fun showBottomSheet(
    ticketId:MutableStateFlow<Long>,
    itemId:Long,
    bottomSheetMemberModel:MutableStateFlow<MemberModel>,
    memberModel: MemberModel,
    bottomSheetState:ModalBottomSheetState
){
    ticketId.value =  itemId
    bottomSheetMemberModel.value.user.role = memberModel.user.role
    bottomSheetMemberModel.value.user.studentID = memberModel.user.studentID
    bottomSheetMemberModel.value.user.profile = memberModel.user.profile
    bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
}