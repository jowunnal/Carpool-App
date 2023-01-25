package com.mate.carpool.ui.screen.home.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.mate.carpool.R
import com.mate.carpool.ui.theme.MateTheme
import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.item.MemberRole
import com.mate.carpool.data.model.domain.item.TicketType
import com.mate.carpool.data.model.domain.item.getDayStatus
import com.mate.carpool.data.model.domain.item.getTicketType
import com.mate.carpool.ui.base.BaseBottomSheetDialogFragment
import com.mate.carpool.ui.navigation.NavigationGraph
import com.mate.carpool.ui.screen.home.vm.*
import com.mate.carpool.ui.screen.reserveDriver.fragment.ReserveDriverFragment
import com.mate.carpool.ui.screen.reservePassenger.ReservePassengerFragment
import com.mate.carpool.ui.theme.neutral20
import com.mate.carpool.ui.theme.neutral30
import com.mate.carpool.ui.theme.neutral50
import com.mate.carpool.ui.theme.primary50
import com.mate.carpool.ui.theme.red50
import com.mate.carpool.ui.util.IntUtils.toSp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MateTheme() {
                    MainView(
                        onNavigateToCreateCarpool = { findNavController().navigate(R.id.action_homeFragment_to_createTicketBoardingAreaFragment) },
                        onNavigateToProfileView = { findNavController().navigate(R.id.action_homeFragment_to_profileLookUpFragment) }
                    )
                }
            }
        }
    }
}