package com.mate.carpool.data.model.domain

import androidx.databinding.ObservableField
import com.mate.carpool.data.model.DTO.MemberRequestDTO
import com.mate.carpool.data.model.DTO.MemberTimeTableRequestDTO
import com.mate.carpool.data.model.DTO.MemberTimeTableResponseDTO
import com.mate.carpool.data.model.domain.item.MemberRole
import com.mate.carpool.data.model.domain.item.getMemberRoleDTO

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
) {

    fun asMemberRequestDTO() = MemberRequestDTO(
        this.studentID,
        this.name,
        this.department,
        this.phone.replace("-",""),
        this.role.getMemberRoleDTO(),
        "",
        this.studentDayCodes.asTimeTableListDTO()
    )

    fun List<String>.asTimeTableListDTO() = List<MemberTimeTableRequestDTO>(this.size){
        MemberTimeTableRequestDTO(this[it])
    }

}