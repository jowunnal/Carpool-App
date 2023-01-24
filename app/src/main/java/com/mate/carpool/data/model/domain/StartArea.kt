package com.mate.carpool.data.model.domain

import androidx.compose.runtime.Stable

@Stable
enum class StartArea(val displayName: String) {
    INDONG(displayName = "인동"),
    OKGYE(displayName = "옥계"),
    DEAGU(displayName = "대구"),
    ETC("기타");

    companion object {
        fun findByDisplayName(displayName: String) = values().first { it.displayName == displayName }
    }
}