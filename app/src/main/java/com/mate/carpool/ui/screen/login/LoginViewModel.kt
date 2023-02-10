package com.mate.carpool.ui.screen.login

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.Result
import com.mate.carpool.data.repository.AuthRepository
import com.mate.carpool.data.repository.impl.AuthRepositoryImpl
import com.mate.carpool.ui.base.BaseViewModel
import com.mate.carpool.ui.screen.login.item.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context
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

    fun login() {
        authRepository.login(
            email = uiState.value.email,
            passWord = uiState.value.password
        ).onEach {
            _uiState.update { it.copy(loginSuccess = true) }
        }.catch {

        }.launchIn(viewModelScope)
    }
}


