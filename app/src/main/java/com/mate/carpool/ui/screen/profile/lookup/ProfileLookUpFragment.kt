package com.mate.carpool.ui.screen.profile.lookup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mate.carpool.ui.base.BaseComposeFragment
import com.mate.carpool.ui.composable.rememberLambda
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileLookUpFragment : BaseComposeFragment<ProfileLookUpViewModel>() {

    override val viewModel: ProfileLookUpViewModel by viewModels()
    override val useActionBar: Boolean = false

    @Composable
    override fun Content() {
        val profile by viewModel.profile.collectAsState()

        ProfileLookUpScreen(
            profile = profile,
            onEditClick = rememberLambda {
                profile ?: kotlin.run {
                    showSnackbar("로딩중입니다. 잠시만 기다려주세요.")
                    return@rememberLambda
                }
                val action = ProfileLookUpFragmentDirections
                    .actionProfileLookUpFragmentToProfileModifyFragment(profile!!)
                findNavController().navigate(action)
            },
            onBackClick = rememberLambda { findNavController().popBackStack() },
        )
    }
}