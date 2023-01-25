package com.mate.carpool.ui.screen.report

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

@OptIn(ExperimentalLifecycleComposeApi::class)
@AndroidEntryPoint
class ReportFragment : BaseComposeFragment<ReportViewModel>() {

    private val args: ReportFragmentArgs by navArgs()

    override val useActionBar: Boolean = false
    override val viewModel: ReportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.init(studentId = args.studentId)
    }

    @Composable
    override fun Content() {
        val reason by viewModel.reason.collectAsStateWithLifecycle()
        val description by viewModel.description.collectAsStateWithLifecycle()
        val enableReport by viewModel.enableReport.collectAsStateWithLifecycle()

        ReportScreen(
            selectedReason = reason,
            description = description,
            enableReport = enableReport,
            onDescriptionEdit = viewModel::setDescription,
            onSelectReason = viewModel::selectReason,
            onDeselectReason = viewModel::deselectReason,
            onReportClick = viewModel::report,
            onBackClick = rememberLambda { findNavController().popBackStack() }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { event ->
                    when (event.type) {
                        ReportViewModel.EVENT_FINISH -> {
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }
    }
}