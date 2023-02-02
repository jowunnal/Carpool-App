package com.mate.carpool.ui.screen.profile.lookup.component

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.mate.carpool.R
import com.mate.carpool.data.model.item.MemberRole
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.screen.home.compose.component.ProfileImage
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.theme.primary10
import com.mate.carpool.ui.util.FileUtils.getImageMultipartBody
import com.mate.carpool.ui.util.displayName
import com.mate.carpool.ui.util.tu
import com.mate.carpool.util.formatPhoneNumber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.time.DayOfWeek

@Suppress("FunctionName")
fun LazyListScope.UserTopInfoItem(
    profileImageUrl: String,
    name: String,
    studentId: String,
    department: String,
    phone: String,
    modifier: Modifier = Modifier,
    setProfileImage: (MultipartBody.Part) -> Unit,
) {
    item {
        UserTopInfo(
            modifier = modifier,
            profileImageUrl = profileImageUrl,
            name = name,
            studentId = studentId,
            department = department,
            phone = phone,
            setProfileImage = setProfileImage
        )
    }
}

@Suppress("FunctionName")
fun LazyListScope.UserBottomInfoItem(
    daysOfUser: List<DayOfWeek>,
    userRole: MemberRole?,
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
    modifier: Modifier = Modifier,
    setProfileImage: (MultipartBody.Part) -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->

        if (uri != null) {
            coroutineScope.launch(Dispatchers.IO) {
                setProfileImage(
                    getImageMultipartBody(
                        uri = uri,
                        context = context
                    )
                )
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            ProfileImage(
                profileImage = profileImageUrl,
                modifier = Modifier
                    .clip(CircleShape)
                    .fillMaxSize()
                    .clickable { galleryLauncher.launch("image/*") }
                    .background(primary10),
                defaultImage = R.drawable.ic_profile
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
    daysOfUser: List<DayOfWeek>,
    userRole: MemberRole?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InfoText(type = "사용 요일", value = daysOfUser.joinToString(", ") { it.displayName })
        VerticalSpacer(10.dp)
        InfoText(type = "사용자 유형", value = userRole?.displayName ?: "")
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
        setProfileImage = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun UserBottomInfo() {
    UserBottomInfo(
        daysOfUser = listOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY),
        userRole = MemberRole.DRIVER
    )
}