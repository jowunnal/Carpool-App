package com.mate.carpool.ui.screen.profile.modify

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.data.model.item.MemberRole
import com.mate.carpool.ui.composable.SimpleTopAppBar
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.screen.profile.modify.component.ConfirmButton
import com.mate.carpool.ui.screen.profile.modify.component.DaysOfUseSelector
import com.mate.carpool.ui.screen.profile.modify.component.PhoneTextField
import com.mate.carpool.ui.screen.profile.modify.component.UserRoleSelector
import com.mate.carpool.ui.theme.white
import com.mate.carpool.util.MatePreview
import java.time.DayOfWeek

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileModifyScreen(
    phone: String,
    userRole: MemberRole,
    daysOfUse: List<DayOfWeek>,
    enableConfirm: Boolean,
    onEditPhone: (String) -> Unit,
    onUserRoleChange: (MemberRole) -> Unit,
    onDayOfUseSelect: (DayOfWeek) -> Unit,
    onDayOfUseDeselect: (DayOfWeek) -> Unit,
    onConfirmClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        containerColor = white,
        topBar = {
            SimpleTopAppBar(
                title = "내 정보 수정",
                onBackClick = onBackClick,
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(white)
                .padding(paddingValues)
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 36.dp),
        ) {
            PhoneTextField(
                value = phone,
                onValueChange = onEditPhone,
            )
            VerticalSpacer(height = 40.dp)
            UserRoleSelector(
                userRole = userRole,
                onUserRoleChange = onUserRoleChange
            )
            VerticalSpacer(height = 40.dp)
            DaysOfUseSelector(
                daysOfUse = daysOfUse,
                onDayOfUseSelect = onDayOfUseSelect,
                onDayOfUseDeselect = onDayOfUseDeselect,
            )
            Spacer(modifier = Modifier.weight(1f))
            ConfirmButton(
                enabled = enableConfirm,
                onClick = onConfirmClick
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun ProfileModifyScreenPreview() = MatePreview {
    ProfileModifyScreen(
        phone = "010123456789",
        userRole = MemberRole.PASSENGER,
        daysOfUse = listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY),
        enableConfirm = true,
        onEditPhone = {},
        onUserRoleChange = {},
        onDayOfUseSelect = {},
        onDayOfUseDeselect = {},
        onConfirmClick = {},
        onBackClick = {},
    )
}