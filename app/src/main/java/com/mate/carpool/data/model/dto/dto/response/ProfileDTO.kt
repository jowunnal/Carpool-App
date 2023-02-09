package com.mate.carpool.data.model.dto.dto.response

import com.google.gson.annotations.SerializedName
import com.mate.carpool.data.model.domain.domain.UserModel
import com.mate.carpool.data.model.item.MemberRole


data class ProfileDTO(
    @SerializedName("memberId") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("profileImageUrl") val image: String,
    @SerializedName("email") val email: String,
    @SerializedName("type") val role: String
) {
    fun asUserDomainModel() = UserModel(
        id = id,
        name = name,
        email = email,
        passWord = "",
        profileImage = image,
        role = MemberRole.findByDisplayName(role),
        passengerId = ""
    )
}
