package com.mate.carpool.data.model.domain

import androidx.databinding.ObservableField
import com.mate.carpool.data.model.DTO.MemberTimeTableResponseDTO
import com.mate.carpool.data.model.domain.item.MemberRole

/**
 * 사용자 정보를 담는 클래스
 */

data class UserModel(
    var name: String = "",
    var studentID:String = "",
    var department:String = "",
    val phone:String = "",
    var role:MemberRole = MemberRole.Driver,
    var profile:String = "",
    var studentDayCodes: List<String> = emptyList(),
    var passengerId:Long = 0
)