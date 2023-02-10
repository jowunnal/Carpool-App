package com.mate.carpool.ui.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.mate.carpool.R
import com.mate.carpool.ui.composable.rememberLambda
import com.mate.carpool.ui.theme.MateTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class NavigationFragment : Fragment() {
    private val args: NavigationFragmentArgs by navArgs()
    private var backPressedTime = 0L

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
                MateTheme {
                    this@NavigationFragment.Content()
                    OnBackPressed()
                }
            }
        }
    }

    @Composable
    private fun Content() {
        NavigationGraph(
            navArgs = args,
            navController = rememberNavController(),
            onNavigateToCreateCarpool = rememberLambda {
                findNavController().navigate(R.id.action_homeFragment_to_createTicket)
            },
            onNavigateToProfileView = rememberLambda {
                findNavController().navigate(R.id.action_homeFragment_to_profileLookUpFragment)
            },
            onNavigateToOnBoarding = rememberLambda {
                findNavController().navigate(R.id.action_homeFragment_to_onboardingFragment)
            }
        )
    }

    @Composable
    private fun OnBackPressed() {
        BackHandler {
            if (System.currentTimeMillis() - backPressedTime >= 2000L) {
                backPressedTime = System.currentTimeMillis()
                Snackbar.make(
                    requireView().rootView,
                    "뒤로 가기 버튼을 한 번 더 누르면 종료됩니다.",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                finishAffinity(requireActivity())
                System.runFinalization()
                exitProcess(0)
            }
        }
    }
}
