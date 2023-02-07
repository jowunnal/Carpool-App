package com.mate.carpool.ui.screen.login.item

import com.mate.carpool.data.model.domain.domain.UserModel

data class LoginUiState(
    val email: String,
    val password: String,
    val showPassword: Boolean,
    val loginSuccess: Boolean,
    val invalidEmail: Boolean,
    val invalidPassword: Boolean,
) {
    val enableLogin: Boolean
        get() = email.isNotBlank() && password.isNotBlank()

    fun asUserDomainModel() = UserModel(
        name = "",
        email = email,
        passWord = password
    )

    companion object {
        fun getInitialValue() = LoginUiState(
            email = "",
            password = "",
            showPassword = false,
            loginSuccess = false,
            invalidEmail = false,
            invalidPassword = false
        )
    }
}