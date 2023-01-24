package com.mate.carpool.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberLambda(lambda: () -> Unit): () -> Unit = remember { lambda }