package com.mate.carpool.ui.screen.profile.modify.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.data.model.item.MemberRole
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.theme.neutral50
import com.mate.carpool.ui.theme.primary50
import com.mate.carpool.ui.theme.white
import com.mate.carpool.ui.util.tu

@Composable
fun UserRoleSelector(
    userRole: MemberRole,
    modifier: Modifier = Modifier,
    onUserRoleChange: (MemberRole) -> Unit,
) {
    val indicatorBias by animateFloatAsState(
        when (userRole) {
            MemberRole.PASSENGER -> 1f
            MemberRole.DRIVER -> -1f
            MemberRole.ADMIN -> -1f  // TODO 확인
        }
    )

    Column(modifier) {
        Text(
            text = "사용자 유형",
            color = black,
            fontWeight = FontWeight.Normal,
            fontSize = 13.tu
        )
        VerticalSpacer(height = 6.dp)
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, primary50, RoundedCornerShape(35.dp))
                .background(white, RoundedCornerShape(35.dp))
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(100.dp))
                        .clickable { onUserRoleChange(MemberRole.DRIVER) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "드라이버",
                        color = primary50,
                        fontSize = 16.tu,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(100.dp))
                        .clickable { onUserRoleChange(MemberRole.PASSENGER) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "패신저",
                        color = primary50,
                        fontSize = 16.tu,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            VerticalSpacer(height = 6.dp)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(maxWidth / 2)
                    .padding(3.dp)
                    .background(primary50, RoundedCornerShape(100.dp))
                    .align(BiasAlignment(indicatorBias, 0f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userRole.displayName,
                    color = white,
                    fontSize = 16.tu,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
        VerticalSpacer(height = 8.dp)
        Text(
            text = "드라이버는 자차보유자를, 패신저는 승객(이용자)를 뜻합니다.\n추후에 변경 가능합니다.",
            color = neutral50,
            fontWeight = FontWeight.Normal,
            fontSize = 13.tu,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UserRoleSelectorPreview1() {
    var currentUserRole by remember { mutableStateOf(MemberRole.PASSENGER) }

    UserRoleSelector(userRole = MemberRole.DRIVER) { userRole ->
        currentUserRole = userRole
    }
}

@Preview(showBackground = true)
@Composable
private fun UserRoleSelectorPreview2() {
    UserRoleSelector(userRole = MemberRole.PASSENGER) {}
}