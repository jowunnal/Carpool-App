package com.mate.carpool.ui.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.findNavController
import com.mate.carpool.R
import com.mate.carpool.ui.composable.rememberLambda
import com.mate.carpool.ui.theme.MateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavigationFragment : Fragment() {

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
                    this@NavigationFragment.Content()
                }
            }
        }
    }

    @Composable
    private fun Content() {
        NavigationGraph(
            navController = rememberNavController(),
            onNavigateToCreateCarpool = rememberLambda {
                findNavController().navigate(R.id.action_homeFragment_to_createTicketBoardingAreaFragment)
            },
            onNavigateToProfileView = rememberLambda {
                findNavController().navigate(R.id.action_homeFragment_to_profileLookUpFragment)
            }
        )
    }
}
