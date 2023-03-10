package com.mate.carpool.data.repository.impl

import com.mate.carpool.AutoLoginPreferences
import com.mate.carpool.data.datasource.AutoLoginDataSource
import com.mate.carpool.data.model.domain.domain.ResponseModel
import com.mate.carpool.data.model.dto.dto.request.LoginDTO
import com.mate.carpool.data.model.dto.dto.request.ReissueDTO
import com.mate.carpool.data.model.dto.dto.request.SignUpDTO
import com.mate.carpool.data.model.dto.dto.response.LoginResponse
import com.mate.carpool.data.repository.AuthRepository
import com.mate.carpool.data.service.APIService
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: APIService, private val autoLoginDataSource: AutoLoginDataSource
) : AuthRepository {

    override val autoLoginInfo: Flow<AutoLoginPreferences> = autoLoginDataSource.autoLoginInfo

    override fun login(
        email: String, passWord: String
    ): Flow<String> = flow {
        emit(
            apiService.login(
                LoginDTO.fromDomain(
                    email = email, passWord = passWord
                )
            )
        )
    }.map { response ->
        autoLoginDataSource.updateAutoLoginInfo(
            accessToken = response.accessToken, refreshToken = response.refreshToken
        )
        response.accessToken
    }

    override fun logout(): Flow<String> = flow {
        emit(apiService.logout())
    }.map { response ->
        response.message
    }

    override fun reNewAccessToken(): Flow<LoginResponse> = flow {
        autoLoginInfo.collect { pref ->
            emit(
                apiService.reNewAccessToken(
                    ReissueDTO.fromDomain(
                        accessToken = pref.accessToken, refreshToken = pref.refreshToken
                    )
                )
            )
        }
    }

    override suspend fun updateToken(accessToken: String, refreshToken: String) {
        autoLoginDataSource.updateAutoLoginInfo(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    override fun withDraw(): Flow<ResponseModel> = flow {
        emit(apiService.withDraw())
    }.map { response ->
        response.asResponseModel()
    }

    override fun signUp(
        email: String, passWord: String, name: String
    ): Flow<String> = flow {
        emit(
            apiService.signUp(
                SignUpDTO.fromDomain(
                    email = email, passWord = passWord, name = name
                )
            )
        )
    }.map { response ->
        response.message
    }
}