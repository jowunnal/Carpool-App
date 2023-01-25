package com.mate.carpool.util

import android.util.Log

object ImageUtil {
    private const val TAG = "ImageUtil"

    fun getImageUrl(path: String): String {
        val result = Constant.BASE_URL + "/member/profile" + path
        Log.d(TAG, "created image url = $result")
        return result
    }
}