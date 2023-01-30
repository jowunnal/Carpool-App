@file:OptIn(ExperimentalMaterial3Api::class)

package com.mate.carpool.ui.composable.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mate.carpool.ui.composable.SimpleTopAppBar
import com.mate.carpool.ui.theme.white

@Composable
fun CommonLayout(
    title: String?,
    onBackClick: () -> Unit,
    snackBarHost: @Composable (SnackbarHostState) -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    androidx.compose.material.Scaffold(
        backgroundColor = white,
        topBar = {
            SimpleTopAppBar(
                title = title ?: "",
                onBackClick = onBackClick,
            )
        },
        snackbarHost = snackBarHost
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(white)
                .padding(paddingValues)
                .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 36.dp),
        ) { content() }
    }
}