package com.mate.carpool.ui.screen.profile.modify

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mate.carpool.ui.base.BaseComposeFragment
import com.mate.carpool.ui.composable.rememberLambda
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalLifecycleComposeApi::class)
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
        val profile by viewModel.profile.collectAsStateWithLifecycle()
        val enableConfirm by viewModel.enableConfirm.collectAsStateWithLifecycle()

        ProfileModifyScreen(
            phone = profile.phone,
            userRole = profile.userRole,
            daysOfUse = profile.daysOfUse,
            enableConfirm = enableConfirm,
            onEditPhone = viewModel::setPhone,
            onUserRoleChange = viewModel::setUserRole,
            onDayOfUseSelect = viewModel::addDayOfUse,
            onDayOfUseDeselect = viewModel::removeDayOfUse,
            onConfirmClick = viewModel::save,
            onBackClick = rememberLambda { findNavController().popBackStack() },
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { event ->
                    when (event.type) {
                        ProfileModifyViewModel.EVENT_FINISH -> {
                            val action = ProfileModifyFragmentDirections
                                .actionProfileModifyFragmentToProfileLookUpFragment(needRefresh = true)
                            findNavController().navigate(action)
                        }
                    }
                }
            }
        }
    }
}