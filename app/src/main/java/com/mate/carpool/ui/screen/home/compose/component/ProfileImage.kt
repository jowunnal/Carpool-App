package com.mate.carpool.ui.screen.home.compose.component

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.mate.carpool.R
import com.mate.carpool.ui.theme.MateTheme
import com.mate.carpool.ui.theme.neutral20
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProfileImage(
    profileImage: String,
    modifier: Modifier,
    failure: @Composable () -> Unit = {
        Icon(
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = null
        )
    },
    enabled: Boolean = true
) {
    GlideImage(
        imageModel = { profileImage },
        modifier = modifier,
        loading = {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        },
        failure = {
            failure()
        },
        imageOptions = if (enabled) ImageOptions(alpha = 1f) else ImageOptions(
            colorFilter = ColorFilter.tint(
                neutral20
            ), alpha = 0.5f
        ),
        previewPlaceholder = R.drawable.ic_profile
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewProfileImage() =
    MateTheme {
        ProfileImage(
            profileImage = "",
            modifier = Modifier,
            failure = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_small),
                    contentDescription = null
                )
            },
            enabled = true
        )
    }

@Preview(showBackground = true)
@Composable
private fun PreviewProfileImageDisabled() =
    MateTheme {
        ProfileImage(
            profileImage = "",
            modifier = Modifier,
            failure = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_small),
                    contentDescription = null
                )
            },
            enabled = false
        )
    }