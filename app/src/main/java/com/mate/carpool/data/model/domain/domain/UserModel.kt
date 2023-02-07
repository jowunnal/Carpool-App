package com.mate.carpool.data.model.domain.domain

import com.mate.carpool.data.model.dto.dto.request.LoginDTO
import com.mate.carpool.data.model.dto.dto.request.SignUpDTO

data class UserModel(
    val name: String,
    val email: String,
    val passWord: String
) {
    fun asLoginRequestDTO() = LoginDTO(
        email = email,
        passWord = passWord
    )

    fun asSignUpRequestDTO() = SignUpDTO(
        userName = name,
        email = email,
        passWord = passWord
    )
}