package com.mate.carpool.ui.screen.profile.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mate.carpool.R
import com.mate.carpool.data.model.domain.UserRole
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.theme.primary10
import com.mate.carpool.ui.util.tu
import com.mate.carpool.util.formatPhoneNumber

@Suppress("FunctionName")
fun LazyListScope.UserTopInfoItem(
    profileImageUrl: String,
    name: String,
    studentId: String,
    department: String,
    phone: String,
    modifier: Modifier = Modifier
) {
    item {
        UserTopInfo(
            modifier = modifier,
            profileImageUrl = profileImageUrl,
            name = name,
            studentId = studentId,
            department = department,
            phone = phone,
        )
    }
}

@Suppress("FunctionName")
fun LazyListScope.UserBottomInfoItem(
    daysOfUser: List<String>,
    userRole: UserRole?,
    modifier: Modifier = Modifier
) {
    item {
        UserBottomInfo(
            modifier = modifier,
            daysOfUser = daysOfUser,
            userRole = userRole
        )
    }
}

@Composable
private fun UserTopInfo(
    profileImageUrl: String,
    name: String,
    studentId: String,
    department: String,
    phone: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            AsyncImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .fillMaxSize()
                    .background(primary10),
                model = profileImageUrl,
                contentDescription = "profile image"
            )
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(24.dp)
                    .background(Color(0xFF8CC3FF))
                    .padding(5.dp),
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = "edit profile image"
            )
        }
        VerticalSpacer(9.dp)
        InfoText(type = "이름", value = name)
        VerticalSpacer(10.dp)
        InfoText(type = "학번", value = studentId)
        VerticalSpacer(10.dp)
        InfoText(type = "학과", value = department)
        VerticalSpacer(10.dp)
        InfoText(type = "휴대폰 번호", value = phone.formatPhoneNumber())
    }
}

@Composable
private fun UserBottomInfo(
    daysOfUser: List<String>,
    userRole: UserRole?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InfoText(type = "사용 요일", value = daysOfUser.joinToString(", "))
        VerticalSpacer(10.dp)
        InfoText(type = "사용자 유형", value = userRole?.text ?: "")
    }
}

@Composable
private fun InfoText(type: String, value: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = type,
            fontSize = 16.tu,
            fontWeight = FontWeight.Bold,
            color = black
        )
        Text(
            text = value,
            fontSize = 16.tu,
            fontWeight = FontWeight.Normal,
            color = black
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UserTopInfoPreview() {
    UserTopInfo(
        profileImageUrl = "",
        name = "강금실",
        studentId = "111111",
        department = "미학과",
        phone = "01011112222",
    )
}

@Preview(showBackground = true)
@Composable
private fun UserBottomInfo() {
    UserBottomInfo(
        daysOfUser = listOf("월", "수", "금"),
        userRole = UserRole.DRIVER
    )
}