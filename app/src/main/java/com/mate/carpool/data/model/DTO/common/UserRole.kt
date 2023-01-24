package com.mate.carpool.data.model.dto.common

import androidx.compose.runtime.Stable

@Stable
enum class UserRole(val displayName: String) {

    DRIVER(displayName = "드라이버"),
    PASSENGER(displayName = "패신저"),
    ADMIN(displayName = "관리자")
}