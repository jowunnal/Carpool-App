package com.mate.carpool.data.repository.impl

import com.mate.carpool.AutoLoginPreferences
import com.mate.carpool.data.Result
import com.mate.carpool.data.callApi
import com.mate.carpool.data.datasource.AutoLoginDataSource
import com.mate.carpool.data.model.domain.domain.UserModel
import com.mate.carpool.data.model.dto.dto.request.LoginDTO
import com.mate.carpool.data.model.dto.dto.request.SignUpDTO
import com.mate.carpool.data.model.item.StudentItem
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.model.response.ResponseMessage
import com.mate.carpool.data.repository.AuthRepository
import com.mate.carpool.data.service.APIService
import com.mate.carpool.ui.util.HandleFlowUtils.handleFlowApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: APIService,
    private val autoLoginDataSource: AutoLoginDataSource
) : AuthRepository {

    override val autoLoginInfo: Flow<AutoLoginPreferences> = autoLoginDataSource.autoLoginInfo

    override fun signUp(
        email: String,
        passWord: String,
        name: String
    ): Flow<String> = flow {
        emit(apiService.signUp(SignUpDTO.fromDomain(
            email = email,
            passWord = passWord,
            name = name
        )))
    }.map { response ->
        response.message
    }

    override fun login(
        email: String,
        passWord: String
    ): Flow<String> = flow {
        emit(apiService.login(LoginDTO.fromDomain(
            email = email,
            passWord = passWord
        )))
    }.map { response ->
        autoLoginDataSource.updateAutoLoginInfo(response.accessToken)
        response.accessToken
    }

    override fun logout(): Flow<String> = flow {
        emit(apiService.logout())
    }.map { response ->
        response.message
    }

    override fun checkAccessTokenIsExpired(): Flow<Result<ResponseMessage>> = callApi {
        apiService.checkAccessTokenIsExpired()
    }.map {
        when(it){
            is Result.Loading -> {
                Result.Loading
            }

            is Result.Success -> {
                Result.Success(
                    ResponseMessage(
                        status = it.data.hashCode(),
                        message = it.data,
                        code = it.data
                    )
                )
            }
            is Result.Error -> {
                Result.Error(
                    it.message
                )
            }
        }
    }
}