package com.mate.carpool.ui.screen.signup

import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.Result
import com.mate.carpool.data.repository.AuthRepository
import com.mate.carpool.ui.base.BaseViewModel
import com.mate.carpool.ui.base.SnackBarMessage
import com.mate.carpool.util.substring
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

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

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState.getInitialValue())
    val uiState = _uiState.asStateFlow()

    fun setName(value: String) = _uiState.update { state ->
        val pattern = Regex("[ㄱ-ㅎ|가-힣]")
        val result = value
            .filter { pattern.matches(it.toString()) }
            .substring(4)
        state.copy(name = result, invalidName = false)
    }

    fun setEmail(value: String) = _uiState.update { state ->
        val pattern = Regex("[.@a-zA-Z0-9]")
        val result = value.filter { pattern.matches(it.toString()) }
        state.copy(email = result, invalidEmail = false)
    }

    fun setPassword(value: String) = _uiState.update { state ->
        state.copy(password = value, invalidPassword = false)
    }

    fun setShowPassword(value: Boolean) =
        _uiState.update { state -> state.copy(showPassword = value) }

    // TODO 로그인 성공 시, 홈화면 이동 후 성공 메시지 출력
    fun signUp() {
        if (!checkInput()) return

        authRepository.signUp(
            name = uiState.value.name,
            email = uiState.value.email,
            password = uiState.value.password
        ).onEach { result ->
            when (result) {
                is Result.Loading -> {

                }

                is Result.Success -> {
                    // TODO 회원가입 성공 시, 서버에서 로그인까지 처리해달라고 요청?
                    _uiState.update { it.copy(signUpSuccess = true) }
                    //emitSnackbar(SnackBarMessage(headerMessage = "회원가입 성공 어쩌구"))
                }

                is Result.Error -> {
                    when (result.message) {
                        "invalidName" -> _uiState.update { it.copy(invalidName = true) }
                        "invalidEmail" -> _uiState.update { it.copy(invalidEmail = true) }
                        "invalidPassword" -> _uiState.update { it.copy(invalidPassword = true) }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun checkInput(): Boolean {
        val namePattern = Regex("^[가-힣]+")
        val emailPattern = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        val passwordPattern = Regex("^[a-zA-Z0-9]+")
        val password = uiState.value.password

        if (!uiState.value.name.matches(namePattern)) {
            _uiState.update { it.copy(invalidName = true) }  // UI에서 처리 없음
            return false
        }

        if (!uiState.value.email.matches(emailPattern)) {
            _uiState.update { it.copy(invalidEmail = true) }
            return false
        }

        if (password.length < 8 || password.length > 20
            || !password.matches(passwordPattern)
            || !password.contains(Regex("[a-z]"))
            || !password.contains(Regex("[A-Z]"))
            || !password.contains(Regex("[0-9]"))
        ) {
            _uiState.update { it.copy(invalidPassword = true) }
            return false
        }

        return true
    }
}