package com.mate.carpool.ui.screen.profile.modify

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mate.carpool.ui.base.BaseComposeFragment
import com.mate.carpool.ui.composable.rememberLambda
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileModifyFragment : BaseComposeFragment<ProfileModifyViewModel>() {

    private val args: ProfileModifyFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ProfileModifyViewModel.InitialProfileAssistedFactory
    override val viewModel: ProfileModifyViewModel by viewModels {
        ProfileModifyViewModel.provideFactory(
            assistedFactory = viewModelFactory,
            initialProfile = args.profile
        )
    }
    override val useActionBar: Boolean = false

    @Composable
    override fun Content() {
        val profile by viewModel.profile.collectAsState()

        ProfileModifyScreen(
            phone = profile.phone,
            userRole = profile.userRole,
            daysOfUse = profile.daysOfUse,
            onEditPhone = viewModel::setPhone,
            onUserRoleChange = viewModel::setUserRole,
            onDayOfUseSelect = viewModel::addDayOfUse,
            onDayOfUseDeselect = viewModel::removeDayOfUse,
            onConfirmClick = viewModel::save,
            onBackClick = rememberLambda { findNavController().popBackStack() },
        )

    }
}