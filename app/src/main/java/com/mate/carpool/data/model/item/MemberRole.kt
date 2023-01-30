package com.mate.carpool.data.model.item

enum class MemberRole{
    Passenger,
    Driver
}

fun MemberRole.getMemberRoleDomain() =
    when(this) {
        MemberRole.Passenger -> { "패신저" }
        MemberRole.Driver -> { "드라이버" }
    }

fun MemberRole.getMemberRoleDTO() =
    when(this) {
        MemberRole.Passenger -> { "PASSENGER" }
        MemberRole.Driver -> { "DRIVER" }
    }