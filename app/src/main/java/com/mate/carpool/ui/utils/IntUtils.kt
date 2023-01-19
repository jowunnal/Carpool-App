package com.mate.carpool.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

object IntUtils {

    @JvmStatic
    @Composable
    fun Int.toSp() = with(LocalDensity.current) {  Dp(this@toSp.toFloat()).toSp()  }
}