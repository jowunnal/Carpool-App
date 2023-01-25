package com.mate.carpool.ui.screen.home.compose.component

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.mate.carpool.data.model.domain.item.getTicketType
import com.mate.carpool.ui.base.BaseBottomSheetDialogFragment
import com.mate.carpool.ui.screen.home.vm.HomeBottomSheetViewModelInterface
import com.mate.carpool.ui.screen.home.vm.PreviewHomeBottomSheetViewModel
import com.mate.carpool.ui.screen.reservePassenger.ReservePassengerFragment
import com.mate.carpool.ui.theme.neutral30
import com.mate.carpool.ui.theme.neutral50
import com.mate.carpool.ui.theme.primary50
import com.mate.carpool.ui.util.tu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeBottomSheetContent(
    bottomSheetState: ModalBottomSheetState,
    bottomSheetMemberModel: MutableStateFlow<MemberModel>,
    homeCarpoolBottomSheetViewModel: HomeBottomSheetViewModelInterface,
    reNewHomeListener: BaseBottomSheetDialogFragment.Renewing,
    fragmentManager: FragmentManager
){
    val ticketDetail by homeCarpoolBottomSheetViewModel.carpoolTicketState.collectAsStateWithLifecycle()
    val newPassengerState by homeCarpoolBottomSheetViewModel.newPassengerState.collectAsStateWithLifecycle()
    val toastMessage = homeCarpoolBottomSheetViewModel.toastMessage.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

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
            ).show(fragmentManager, "passenger reservation")
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
                    .border(1.dp, neutral30, RoundedCornerShape(5.dp))
                    .padding(top = 8.dp, start = 12.dp, end = 12.dp, bottom = 8.dp)
            )
            {
                Text(text = "출발지", fontSize = 13.tu, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = ticketDetail.startArea, fontSize = 18.tu)
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
                    .border(1.dp, neutral30, RoundedCornerShape(5.dp))
                    .padding(top = 8.dp, start = 12.dp, end = 12.dp, bottom = 8.dp)
            ) {
                Text(text = "도착지", fontSize = 13.tu, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = ticketDetail.endArea, fontSize = 18.tu)
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
                    fontSize = 12.tu,
                    color = neutral50,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = ticketDetail.memberName,
                    fontSize = 14.tu,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
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
                    Toast.makeText(context,"드라이버는 탑승하기를 할 수 없습니다", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(primary50),
            shape = RoundedCornerShape(100.dp)
        )
        {
            Text(
                text = "탑승하기",
                color = Color.White,
                fontWeight = FontWeight.W900,
                fontSize = 18.tu
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun PreviewBottomSheet(){
    HomeBottomSheetContent(
        bottomSheetState = ModalBottomSheetState(ModalBottomSheetValue.Expanded),
        bottomSheetMemberModel = PreviewHomeBottomSheetViewModel.memberModel,
        homeCarpoolBottomSheetViewModel = PreviewHomeBottomSheetViewModel,
        reNewHomeListener = object : BaseBottomSheetDialogFragment.Renewing(){ override fun onRewNew() {} },
        fragmentManager = object : FragmentManager(){}
    )
}