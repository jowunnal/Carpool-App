package com.mate.carpool.ui.utils

import android.util.DisplayMetrics
import androidx.fragment.app.FragmentActivity

object LayoutParamsUtils {
    @JvmStatic
    fun getBottomSheetDialogDefaultHeight(rate:Int,activity: FragmentActivity): Int {
        return getWindowHeight(activity) * rate / 100
    }

    private fun getWindowHeight(activity:FragmentActivity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    @JvmStatic
    fun getBottomSheetDialogDefaultWidth(rate:Int,activity: FragmentActivity): Int {
        return getWindowWidth(activity) * rate / 100
    }

    private fun getWindowWidth(activity:FragmentActivity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }
}