package com.mate.carpool.ui.screen.signup.item

import com.mate.carpool.data.model.domain.domain.UserModel

data class SignUpUiState(
    val name: String,
    val email: String,
    val password: String,
    val showPassword: Boolean,
    val signUpSuccess: Boolean,
    val invalidName: Boolean,
    val invalidEmail: Boolean,
    val invalidPassword: Boolean,
) {
    val enableSignUp: Boolean
        get() = name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()

    fun asUserDomainModel() = UserModel(
        name = name,
        email = email,
        passWord = password
    )

    companion object {
        fun getInitialValue() = SignUpUiState(
            name = "",
            email = "",
            password = "",
            showPassword = false,
            signUpSuccess = false,
            invalidName = false,
            invalidEmail = false,
            invalidPassword = false
        )
    }
}