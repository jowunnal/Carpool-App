package com.mate.carpool.ui.compose

import android.content.ContextWrapper
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.mate.carpool.R
import com.mate.carpool.data.model.domain.MemberRole
import com.mate.carpool.ui.us.home.compose.NavigationGraph
import com.mate.carpool.ui.us.home.vm.HomeCarpoolBottomSheetViewModel
import com.mate.carpool.ui.us.home.vm.HomeCarpoolListViewModel
import com.mate.carpool.ui.us.reserveDriver.fragment.ReserveDriverFragment
import com.mate.carpool.ui.us.reservePassenger.ReservePassengerFragment
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
    ): View? {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MainView(
                    onNavigateToCreateCarpool = {findNavController().navigate(R.id.action_homeFragment_to_createTicketBoardingAreaFragment)}
                )
            }
        }
    }
}

object Colors{
    val Blue_007AFF = Color(0xFF007AFF)
    val Red_E0302D = Color(0xFFE0302D)
    val Gray_DADDE1 = Color(0xFFDADDE1)
    val Gray_A2ABB4 = Color(0xFFA2ABB4)
    val Gray_4E5760 = Color(0xFF4E5760)

}

@Composable
fun MainView(
    onNavigateToCreateCarpool: () -> Unit
){
    val navController = rememberNavController()
    NavigationGraph(
        navController = navController,
        onNavigateToCreateCarpool
    )
}

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeCarpoolSheet(
    onNavigateToCreateCarpool: ()->Unit,
    homeCarpoolBottomSheetViewModel: HomeCarpoolBottomSheetViewModel = hiltViewModel(),
) {
    val bottomSheetState = rememberModalBottomSheetState (
        initialValue = ModalBottomSheetValue.Hidden
    )

    val ticketId = homeCarpoolBottomSheetViewModel.mutableTicketId

    val memberRoleSaveable = listSaver<MemberRole,Any> (
        save = { listOf(it.studentNumber,it.memberRole) },
        restore = { MemberRole(it[0].toString(),it[1].toString()) }
    )

    val memberRole = rememberSaveable(stateSaver = memberRoleSaveable) {
        mutableStateOf(MemberRole())
    }

    val coroutineScope = rememberCoroutineScope()
    val ticketDetail by homeCarpoolBottomSheetViewModel.carpoolTicketState.collectAsStateWithLifecycle()
    val context = (LocalContext.current as ContextWrapper).baseContext as FragmentActivity
    val newPassengerStatue by homeCarpoolBottomSheetViewModel.newPassengerStatue.collectAsStateWithLifecycle()

    ModalBottomSheetLayout(
        sheetContent = {
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
                        .height(56.dp)
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
                        Text(text = "출발지")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = ticketDetail.startArea)
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
                        Text(text = "도착지")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = ticketDetail.endArea)
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
                    ProfileImage(image = R.drawable.icon_main_profile, 50.dp, 47.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = "드라이버",
                            fontSize = 12.sp, color = Colors.Gray_4E5760,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = ticketDetail.memberName,
                            modifier = Modifier.fillMaxWidth()
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
                    text4 = ticketDetail.ticketType
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        if(memberRole.value.memberRole == "PASSENGER"){
                            coroutineScope.launch {
                                homeCarpoolBottomSheetViewModel.addNewPassengerToTicket(ticketDetail.id)
                                if(newPassengerStatue){
                                    ReservePassengerFragment(
                                        memberRole.value.studentNumber
                                    ).show(context.supportFragmentManager,"passenger reservation")
                                    bottomSheetState.animateTo(ModalBottomSheetValue.Hidden)
                                }
                            }
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
                        fontSize = 18.sp
                    )
                }
            }
        },
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(20.dp)
    ) {
        HomeView(
            bottomSheetState,
            coroutineScope,
            onNavigateToCreateCarpool,
            memberRole,
            ticketId
        )
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeView(
    bottomSheetState:ModalBottomSheetState,
    coroutineScope:CoroutineScope,
    onNavigateToCreateCarpool:()->Unit,
    bottomSheetMemberRole:MutableState<MemberRole>,
    ticketId:MutableStateFlow<Int>,
    homeCarpoolListViewModel: HomeCarpoolListViewModel = hiltViewModel()
){
    val carpoolExistState by homeCarpoolListViewModel.carpoolExistState.collectAsStateWithLifecycle()
    val context = (LocalContext.current as ContextWrapper).baseContext as FragmentActivity
    val memberRole by homeCarpoolListViewModel.memberRoleState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = bottomSheetState){
        if(bottomSheetState==ModalBottomSheetState(ModalBottomSheetValue.Hidden)){
            homeCarpoolListViewModel.getCarpoolList()
        }
    }

    Column() {
        HomeAppBar()
        Column(Modifier.padding(start = 16.dp, end = 16.dp,top=32.dp)) {
            HomeCardView(R.drawable.ic_home_folder,"공지사항",R.drawable.ic_home_rightarrow,{})
            Spacer(modifier = Modifier.height(4.dp))

            when(memberRole.memberRole){
                "DRIVER"->{
                    HomeCardView(R.drawable.ic_car,"카풀 모집하기",R.drawable.ic_home_rightarrow,onNavigateToCreateCarpool)
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Column(modifier = Modifier.weight(1f)) {
                HomeCarpoolList(
                    bottomSheetState,
                    memberRole,
                    bottomSheetMemberRole,
                    ticketId
                )
            }
            Button(onClick = {
                coroutineScope.launch {
                    if(carpoolExistState) {
                        when(memberRole.memberRole){
                            "PASSENGER"->{
                                ReservePassengerFragment(
                                    memberRole.studentNumber
                                ).show(context.supportFragmentManager,"passenger reservation")
                            }
                            "DRIVER"->{
                                ReserveDriverFragment().show(context.supportFragmentManager,"driver reservation")
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
                    text = if(!carpoolExistState) "생성한 카풀이 없습니다." else "내 카풀 보기",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W900,
                    color = Color.White
                )
            }
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
                fontSize = 16.sp,
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
    memberRole:MemberRole,
    bottomSheetMemberRole:MutableState<MemberRole>,
    ticketId:MutableStateFlow<Int>
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
        Text(
            text = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Colors.Gray_DADDE1)
        )
        HomeCarpoolItems(
            bottomSheetState,
            memberRole,
            bottomSheetMemberRole,
            ticketId
        )
    }
}

@OptIn( ExperimentalLifecycleComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeCarpoolItems(
    bottomSheetState: ModalBottomSheetState,
    memberRole:MemberRole,
    bottomSheetMemberRole:MutableState<MemberRole>,
    ticketId:MutableStateFlow<Int>,
    homeCarpoolListViewModel: HomeCarpoolListViewModel = hiltViewModel()
){
    val carpoolList by homeCarpoolListViewModel.carpoolListState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val context = (LocalContext.current as ContextWrapper).baseContext as FragmentActivity

    LazyColumn() {
        items(carpoolList) { item ->
            Column(Modifier.clickable {
                if(!bottomSheetState.isVisible){
                    coroutineScope.launch {
                        when(memberRole.memberRole){
                            "DRIVER"->{
                                if(homeCarpoolListViewModel.isTicketIsMineOrNot(item.id)){
                                    ReserveDriverFragment().show(context.supportFragmentManager,"driver reservation")
                                }
                                else{
                                    showBottomSheet(
                                        ticketId,
                                        item.id,
                                        bottomSheetMemberRole,
                                        memberRole,
                                        bottomSheetState
                                    )
                                }
                            }
                            "PASSENGER"->{
                                if(homeCarpoolListViewModel.isTicketIsMineOrNot(item.id)){
                                    ReservePassengerFragment(
                                        memberRole.studentNumber
                                    ).show(context.supportFragmentManager,"passenger reservation")
                                }
                                else {
                                    showBottomSheet(
                                        ticketId,
                                        item.id,
                                        bottomSheetMemberRole,
                                        memberRole,
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
                    ProfileImage(image = R.drawable.icon_main_profile, 50.dp, 47.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Row(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.startArea!!,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = " 출발,",
                        )
                        Text(
                            text = item.dayStatus!!
                            ,
                        )
                        Text(
                            text = item.startTime!!,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Chip(
                        onClick = { /*TODO*/ },
                        colors = when (item.ticketType) {
                            "무료" -> ChipDefaults.chipColors(Colors.Blue_007AFF)
                            "유료" -> ChipDefaults.chipColors(Colors.Red_E0302D)
                            else -> ChipDefaults.chipColors(Color.Gray)
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
                    .weight(1f))
            Text(text = text2,
                Modifier
                    .fillMaxWidth()
                    .weight(1f))
        }
        Column(Modifier
            .weight(1f))
        {
            Text(text = text3,
                Modifier
                    .fillMaxWidth()
                    .weight(1f))
            Text(
                text = text4,
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = when(text4){
                    "무료" -> Colors.Blue_007AFF
                    "유료" -> Colors.Red_E0302D
                    else -> Color.Black
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
private suspend fun showBottomSheet(
    ticketId:MutableStateFlow<Int>,
    itemId:Int,
    bottomSheetMemberRole:MutableState<MemberRole>,
    memberRole: MemberRole,
    bottomSheetState:ModalBottomSheetState
){
    ticketId.value =  itemId
    bottomSheetMemberRole.value.memberRole = memberRole.memberRole
    bottomSheetMemberRole.value.studentNumber = memberRole.studentNumber
    bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
}