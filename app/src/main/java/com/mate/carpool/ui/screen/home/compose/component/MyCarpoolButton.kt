package com.mate.carpool.ui.screen.home.compose.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import com.mate.carpool.data.model.domain.UserRole
import com.mate.carpool.data.model.domain.item.MemberRole
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.composable.button.PrimaryButton
import com.mate.carpool.ui.screen.reserveDriver.fragment.ReserveDriverFragment
import com.mate.carpool.ui.screen.reservePassenger.ReservePassengerFragment
import com.mate.carpool.util.MatePreview

@Composable
fun MyCarpoolButton(
    carpoolExistState: Boolean ,
    userRole: MemberRole,
    fragmentManager: FragmentManager,
    userStudentID: String,
    reNewHomeListener: () -> Unit
) {
    if(carpoolExistState) {
        VerticalSpacer(height = 50.dp)
        PrimaryButton(
            text = "내 카풀 보기",
            onClick = {
                when (userRole) {
                    MemberRole.Passenger -> {
                        ReservePassengerFragment(
                            userStudentID,
                            reNewHomeListener
                        ).show(fragmentManager, "passenger reservation")
                    }

                    MemberRole.Driver -> {
                        ReserveDriverFragment(
                            reNewHomeListener
                        ).show(fragmentManager, "driver reservation")
                    }
                }
            },
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
        )
        VerticalSpacer(height = 22.dp)
    }
}

@Preview
@Composable
private fun PreviewMyCarpoolButton() =
    MatePreview {
        MyCarpoolButton(
            carpoolExistState = true,
            userRole = MemberRole.Driver,
            fragmentManager = object : FragmentManager(){},
            "",
            reNewHomeListener = {}
        )
    }