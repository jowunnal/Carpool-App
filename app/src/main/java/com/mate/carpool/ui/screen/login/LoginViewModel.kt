package com.mate.carpool.ui.screen.login

import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.Result
import com.mate.carpool.data.repository.AuthRepository
import com.mate.carpool.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

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

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState.getInitialValue())
    val uiState = _uiState.asStateFlow()

    fun setEmail(value: String) = _uiState.update { state ->
        val pattern = Regex("[.@a-zA-Z0-9]")
        val result = value.filter { pattern.matches(it.toString()) }
        state.copy(email = result, invalidEmail = false)
    }

    fun setPassword(value: String) {
        _uiState.update {
            it.copy(
                password = value,
                invalidPassword = false
            )
        }
    }

    fun setShowPassword(value: Boolean) {
        _uiState.update { it.copy(showPassword = value) }
    }

    // TODO 로그인 성공 시, 홈화면 이동 후 성공 메시지 출력
    fun login() {
        authRepository.login(email = uiState.value.email, password = uiState.value.password)
            .onEach { result ->
                when (result) {
                    is Result.Loading -> {

                    }

                    is Result.Success -> {
                        _uiState.update { it.copy(loginSuccess = true) }
                    }

                    is Result.Error -> {
                        if (result.message == "invalidEmail") {
                            _uiState.update { it.copy(invalidEmail = true) }

                        } else if (result.message == "invalidPassword") {
                            _uiState.update { it.copy(invalidPassword = true) }
                        }

                    }
                }
            }.launchIn(viewModelScope)
    }
}