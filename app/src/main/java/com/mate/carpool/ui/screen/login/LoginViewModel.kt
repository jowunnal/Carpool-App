package com.mate.carpool.ui.screen.login

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.lifecycle.viewModelScope
import com.mate.carpool.AutoLoginPreferences
import com.mate.carpool.data.Result
import com.mate.carpool.data.repository.AuthRepository
import com.mate.carpool.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
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
    @ApplicationContext private val context:Context
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
        authRepository.login(email = uiState.value.email, password = uiState.value.password)
            .onEach { result ->
                when (result) {
                    is Result.Loading -> {
                    }

                    is Result.Success -> {
                        checkAccessTokenIsExpired()
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

    suspend fun checkAccessTokenIsExpired(){
        authRepository.checkAccessTokenIsExpired().collectLatest {
            when (it) {
                is Result.Loading -> {

                }

                is Result.Success -> {
                }

                is Result.Error -> {
                    temporaryLogin()
                }
            }
        }
    }

    suspend fun temporaryLogin(){
        authRepository.temporaryLogin().collectLatest {
            when (it) {
                is Result.Loading -> {

                }

                is Result.Success -> {
                    context.applicationContext.getSharedPreferences("accessToken", Context.MODE_PRIVATE).edit().putString("accessToken","Bearer ${it.data.message}").apply()
                }

                is Result.Error -> {

                }
            }
        }
    }
}


