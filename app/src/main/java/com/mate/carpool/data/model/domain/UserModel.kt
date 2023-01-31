package com.mate.carpool.data.model.domain

import androidx.compose.runtime.Stable
import com.mate.carpool.data.model.item.MemberRole

/**
 * 사용자 정보를 담는 클래스
 */

@Stable
data class UserModel(
    var name: String = "",
    var studentID: String = "",
    var department: String = "",
    val phone: String = "",
    var role: MemberRole = MemberRole.Driver,
    var profile: String = "",
    var studentDayCodes: List<String> = emptyList(),
    var passengerId: Long = 0
)