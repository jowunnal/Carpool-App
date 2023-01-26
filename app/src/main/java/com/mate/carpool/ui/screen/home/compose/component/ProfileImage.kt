package com.mate.carpool.ui.screen.home.compose.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.mate.carpool.R
import com.mate.carpool.ui.theme.MateTheme
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProfileImage(
    profileImage:String,
    modifier: Modifier
){
    val context = LocalContext.current

    GlideImage(
        imageModel = {
            GlideUrl(
            "http://13.209.43.209:8080/member/profile$profileImage",
            LazyHeaders.Builder().addHeader(
                "Authorization",
                context.getSharedPreferences("accessToken", Context.MODE_PRIVATE).getString("accessToken","")!!
            ).build()
        )},
        modifier = modifier,
        loading = {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        },
        failure = {
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = null
            )
        },
        previewPlaceholder = R.drawable.ic_profile
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewProfileImage() =
    MateTheme {
        ProfileImage(profileImage = "", modifier = Modifier)
    }