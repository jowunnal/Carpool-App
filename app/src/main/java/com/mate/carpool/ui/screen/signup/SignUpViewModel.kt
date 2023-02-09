package com.mate.carpool.ui.screen.signup

import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.Result
import com.mate.carpool.data.model.domain.domain.UserModel
import com.mate.carpool.data.repository.AuthRepository
import com.mate.carpool.data.repository.impl.AuthRepositoryImpl
import com.mate.carpool.ui.base.BaseViewModel
import com.mate.carpool.ui.base.SnackBarMessage
import com.mate.carpool.ui.screen.signup.item.SignUpUiState
import com.mate.carpool.util.substring
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState.getInitialValue())
    val uiState = _uiState.asStateFlow()

    fun setName(value: String) = _uiState.update { state ->
        val pattern = Regex("[ㄱ-ㅎ|가-힣]")
        val result = value.filter { pattern.matches(it.toString()) }.substring(4)
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

    fun signUp() {
        if (checkInput().not()) return

        authRepository.signUp(
            email = uiState.value.email,
            passWord = uiState.value.password,
            name = uiState.value.name
        ).onEach { _ ->
            _uiState.update { it.copy(signUpSuccess = true) }
        }.catch {
            /*when (result.message) {
                "invalidName" -> _uiState.update { it.copy(invalidName = true) }
                "invalidEmail" -> _uiState.update { it.copy(invalidEmail = true) }
                "invalidPassword" -> _uiState.update { it.copy(invalidPassword = true) }
            }*/

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

        if (password.length < 8 || password.length > 20 || !password.matches(passwordPattern) || !password.contains(
                Regex("[a-z]")
            ) || !password.contains(Regex("[A-Z]")) || !password.contains(Regex("[0-9]"))
        ) {
            _uiState.update { it.copy(invalidPassword = true) }
            return false
        }

        return true
    }
}