package com.mate.carpool.data.model.item

import androidx.compose.runtime.Stable

@Stable
enum class MemberRole(val displayName: String) {
    PASSENGER(displayName = "패신저"),
    DRIVER(displayName = "드라이버"),
    ADMIN(displayName = "관리자");

    companion object {

        fun findByDisplayName(name: String) = values().first { role -> role.displayName == name }

        fun getMemberRoleDTO(displayName: String) =
            when(displayName) {
                "패신저" -> { "PASSENGER" }
                "드라이버" -> { "DRIVER" }
                else -> throw IllegalStateException("[MemberRole.getMemberRoleDTO] displayName = $displayName")
            }
    }
}