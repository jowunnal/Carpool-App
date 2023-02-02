package com.mate.carpool.data.model.domain

import androidx.compose.runtime.Stable
import com.mate.carpool.data.model.item.MemberRole

/**
 * 사용자 정보를 담는 클래스
 */

@Stable
data class UserModel(
    var name: String,
    var studentID: String,
    var department: String,
    val phone: String,
    var role: MemberRole,
    var profile: String,
    var studentDayCodes: List<String>,
    var passengerId: Long
) {
    companion object {
        fun getInitValue() = UserModel(
            name = "",
            studentID = "",
            department = "",
            phone = "",
            role = MemberRole.DRIVER,
            profile = "",
            studentDayCodes = emptyList(),
            passengerId = -1L
        )
    }
}