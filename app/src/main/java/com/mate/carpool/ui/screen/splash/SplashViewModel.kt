package com.mate.carpool.ui.screen.splash

import androidx.lifecycle.viewModelScope
import com.mate.carpool.data.repository.AuthRepository
import com.mate.carpool.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val startTime = System.currentTimeMillis()

    init {
        autoLogin()
    }

    private fun autoLogin() {
        authRepository.autoLoginInfo.onEach { info ->
            if (info.accessToken.isBlank() || info.accessToken.isEmpty()) {
                delay(MIN_DELAY_TIME)
                emitEvent(EVENT_GO_TO_LOGIN_SCREEN)
            } else {
                login()
            }
        }.launchIn(viewModelScope)
    }

    private fun login() {
        authRepository.reNewAccessToken()
            .onEach { loginResponse ->
                delayIfNeed()
                authRepository.updateToken(
                    accessToken = loginResponse.accessToken,
                    refreshToken = loginResponse.refreshToken
                )
                emitEvent(EVENT_GO_TO_HOME_SCREEN)
            }.catch {
                delayIfNeed()
                emitEvent(EVENT_GO_TO_LOGIN_SCREEN)
            }.launchIn(viewModelScope)
    }

    private suspend fun delayIfNeed() {
        val delayTime = MIN_DELAY_TIME - System.currentTimeMillis() + startTime
        if (delayTime > 0) {
            delay(delayTime)
        }
    }

    companion object {
        const val MIN_DELAY_TIME = 1_000L
        const val EVENT_GO_TO_LOGIN_SCREEN = "EVENT_GO_TO_LOGIN_SCREEN"
        const val EVENT_GO_TO_HOME_SCREEN = "EVENT_GO_TO_HOME_SCREEN"
    }
}
