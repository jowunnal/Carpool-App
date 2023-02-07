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
import javax.inject.Inject

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
        authRepository.login(uiState.value.asUserDomainModel())
            .onEach { response ->
                when (response) {
                    AuthRepositoryImpl.RESPONSE_SUCCESS -> {
                        checkAccessTokenIsExpired()
                        _uiState.update { it.copy(loginSuccess = true) }
                    }

                    AuthRepositoryImpl.RESPONSE_FAIL -> {
                        /*if (result.message == "invalidEmail") {
                            _uiState.update { it.copy(invalidEmail = true) }

                        } else if (result.message == "invalidPassword") {
                            _uiState.update { it.copy(invalidPassword = true) }
                        }
                         */
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun logout() {
        authRepository.logout().onEach { response ->
            when(response) {

                AuthRepositoryImpl.RESPONSE_SUCCESS -> {
                    emitEvent(LOGOUT_SUCCESS)
                }

                AuthRepositoryImpl.RESPONSE_FAIL -> {
                    emitEvent(LOGOUT_FAILED)
                }
            }
        }
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

    companion object {
        const val LOGOUT_SUCCESS = "LOGOUT_SUCCESS"
        const val LOGOUT_FAILED = "LOGOUT_FAILED"
    }
}


