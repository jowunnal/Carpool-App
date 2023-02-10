@file:OptIn(ExperimentalMaterial3Api::class)

package com.mate.carpool.ui.screen.report

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mate.carpool.ui.composable.SimpleTopAppBar
import com.mate.carpool.ui.composable.VerticalSpacer
import com.mate.carpool.ui.composable.button.LargePrimaryButton
import com.mate.carpool.ui.composable.rememberLambda
import com.mate.carpool.ui.screen.report.component.EtcTextField
import com.mate.carpool.ui.screen.report.component.ReasonOption
import com.mate.carpool.ui.screen.report.item.ReportReason
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.theme.white
import com.mate.carpool.ui.util.tu

@Composable
fun ReportScreen(
    selectedReason: ReportReason?,
    description: String,
    enableReport: Boolean,
    onDescriptionEdit: (String) -> Unit,
    onSelectReason: (ReportReason) -> Unit,
    onDeselectReason: () -> Unit,
    onReportClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    BackHandler(enabled = true) {
        onBackClick()
    }

    Scaffold(
        containerColor = white,
        topBar = {
            SimpleTopAppBar(
                title = "신고하기",
                onBackClick = onBackClick,
            )
        },
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .background(white)
                .padding(paddingValues)
                .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 36.dp),
        ) {
            Text(
                text = "신고하는 이유를 알려주세요.",
                color = black,
                fontWeight = FontWeight.Bold,
                fontSize = 22.tu
            )
            VerticalSpacer(height = 30.dp)

            ReportReason.values().forEachIndexed { index, reason ->
                ReasonOption(
                    reason = reason.description,
                    selected = reason == selectedReason,
                    onSelect = rememberLambda { onSelectReason(reason) },
                    onDeselect = onDeselectReason
                )
                if (index != ReportReason.values().lastIndex) {
                    VerticalSpacer(height = 20.dp)
                }
            }
            VerticalSpacer(height = 12.dp)
            EtcTextField(
                value = description,
                enabled = selectedReason == ReportReason.ETC,
                onValueChange = onDescriptionEdit
            )
            Spacer(modifier = Modifier.weight(1f))
            LargePrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "신고하기",
                enabled = enableReport,
                onClick = onReportClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReportScreenPreview() {
    ReportScreen(
        selectedReason = ReportReason.ABUSE,
        enableReport = true,
        description = "",
        onDescriptionEdit = {},
        onSelectReason = {},
        onDeselectReason = {},
        onReportClick = {},
        onBackClick = {},
    )
}