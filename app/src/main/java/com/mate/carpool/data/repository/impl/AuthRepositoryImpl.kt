package com.mate.carpool.data.repository.impl

import com.mate.carpool.AutoLoginPreferences
import com.mate.carpool.data.Result
import com.mate.carpool.data.callApi
import com.mate.carpool.data.datasource.AutoLoginDataSource
import com.mate.carpool.data.model.domain.domain.UserModel
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

    override fun signUp(userModel: UserModel): Flow<String> = handleFlowApi {
        apiService.signUp(userModel.asSignUpRequestDTO())
    }.map {
        when(it) {
            is ApiResponse.Loading -> { "" }
            is ApiResponse.SuccessResponse -> {
                RESPONSE_SUCCESS
            }
            is ApiResponse.FailResponse -> { RESPONSE_FAIL }
            is ApiResponse.ExceptionResponse -> { RESPONSE_FAIL }
        }
    }

    override fun login(userModel: UserModel): Flow<String> = handleFlowApi {
        apiService.login(userModel.asLoginRequestDTO())
    }.map {
        when(it) {
            is ApiResponse.Loading -> { "" }
            is ApiResponse.SuccessResponse -> {
                autoLoginDataSource.updateAutoLoginInfo(it.responseMessage.accessToken)
                RESPONSE_SUCCESS
            }
            is ApiResponse.FailResponse -> { RESPONSE_FAIL }
            is ApiResponse.ExceptionResponse -> { RESPONSE_FAIL }
        }
    }

    override fun logout(): Flow<String> = handleFlowApi {
        apiService.logout()
    }.map {
        when(it) {
            is ApiResponse.Loading -> { "" }
            is ApiResponse.SuccessResponse -> {
               autoLoginDataSource.updateAutoLoginInfo("")
                RESPONSE_SUCCESS
            }
            is ApiResponse.FailResponse -> { RESPONSE_FAIL }
            is ApiResponse.ExceptionResponse -> { RESPONSE_FAIL }
        }
    }

    override fun temporaryLogin(): Flow<Result<ResponseMessage>> = callApi {
        apiService.postLogin(
            StudentItem(
                "282836",
                "테스트",
                "01028282836"
            )
        )
    }.map {
        when(it){
            is Result.Success -> {
                Result.Success(
                    ResponseMessage(
                        status = it.data.hashCode(),
                        message = it.data.accessToken,
                        code = it.data.refreshTokenExpiresIn
                    )
                )
            }
            is Result.Loading -> {
                Result.Loading
            }
            is Result.Error -> {
                Result.Error(
                    it.message
                )
            }
        }
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

    companion object {
        const val RESPONSE_SUCCESS = "SUCCESS"
        const val RESPONSE_FAIL = "FAIL"
    }
}