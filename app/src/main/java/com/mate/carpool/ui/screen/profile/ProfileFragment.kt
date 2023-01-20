package com.mate.carpool.ui.screen.profile

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mate.carpool.ui.base.BaseComposeFragment
import com.mate.carpool.ui.screen.profile.component.ProfileScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseComposeFragment<ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModels()
    override val useActionBar: Boolean = false

    @Composable
    override fun Content() {
        val profile by viewModel.profile.collectAsState()

        ProfileScreen(
            profile = profile,
            onEditClick = { Toast.makeText(requireContext(), "수정 버튼 누름", Toast.LENGTH_SHORT).show() },
            onBackClick = remember { { findNavController().popBackStack() } },
        )
    }

    @Preview(showBackground = true)
    @Composable
    private fun ContentPreview() {
        Content()
    }
}