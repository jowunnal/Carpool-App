package com.mate.carpool.ui.screen.register

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.R
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.composable.button.PrimaryButton
import com.mate.carpool.ui.composable.layout.CommonLayout
import com.mate.carpool.ui.screen.home.compose.component.ProfileImage
import com.mate.carpool.ui.screen.register.item.RegisterUiState
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.theme.neutral20
import com.mate.carpool.ui.util.tu
import com.mate.carpool.util.MatePreview

@Composable
fun RegisterDriverStepCarImageScreen(
    uiState: RegisterUiState,
    setCarImage: (Uri) -> Unit,
    onNavigatePopBackStack: () -> Unit,
    onNavigateToNextStep: () -> Unit
) {
    BackHandler {
        onNavigatePopBackStack()
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        if(it != null){
            setCarImage(
                it
            )
        }
    }

    CommonLayout(
         title = "드라이버 등록", 
         onBackClick = onNavigatePopBackStack
    ) {
        Text(
            text = "카풀에 이용할\n자동차 사진을 올려주세요.",
            fontSize = 22.tu,
            fontWeight = FontWeight.W700,
            color = black
        )
        VerticalSpacer(height = 30.dp)

        ProfileImage(
            profileImage = uiState.carImage.toString(),
            Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(1.dp, Color.White, CircleShape)
                .background(neutral20)
                .clickable { galleryLauncher.launch("image/*") },
            failure = {
                androidx.compose.material.Icon(
                    painter = painterResource(id = R.drawable.ic_add_small),
                    contentDescription = null,
                    modifier = Modifier.padding(28.dp)
                )
            }
        )

        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(
            text = "다음",
            onClick = onNavigateToNextStep,
            enabled = uiState.invalidCarImage,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewRegisterDriverStepCarImageScreen(){
    MatePreview {
        RegisterDriverStepCarImageScreen(
            uiState = RegisterUiState.getInitValue(),
            setCarImage = {},
            onNavigatePopBackStack = {},
            onNavigateToNextStep = {}
        )
    }
}