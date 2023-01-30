package com.mate.carpool.data.repository.impl

import android.content.Context
import androidx.datastore.core.DataStore
import com.mate.carpool.AutoLoginPreferences
import com.mate.carpool.data.Result
import com.mate.carpool.data.callApi
import com.mate.carpool.data.datasource.AutoLoginDataSource
import com.mate.carpool.data.model.item.StudentItem
import com.mate.carpool.data.model.response.ResponseMessage
import com.mate.carpool.data.repository.AuthRepository
import com.mate.carpool.data.service.APIService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: APIService,
    private val autoLoginDataSource: AutoLoginDataSource
) : AuthRepository {

    override val autoLoginInfo: Flow<AutoLoginPreferences> = autoLoginDataSource.autoLoginInfo

    override fun signUp(name: String, email: String, password: String) = flow {
        when {
            name == "실패" -> {
                emit(Result.Error("invalidName"))
            }

            email == "fail@mate.com" -> {
                emit(Result.Error("invalidEmail"))
            }

            password == "fail" -> {
                emit(Result.Error("invalidPassword"))
            }

            else -> {
                emit(Result.Success(ResponseMessage()))
            }
        }
    }

    override fun login(email: String, password: String): Flow<Result<ResponseMessage>> = flow {
        emit(Result.Loading)

        when {
            email == "fail" -> {
                emit(Result.Error("invalidEmail"))
            }

            password == "fail" -> {
                emit(Result.Error("invalidPassword"))
            }

            else -> {
                // TODO 토큰 저장
                //autoLoginDataSource.updateAutoLoginInfo(token = "")
                emit(Result.Success(ResponseMessage()))
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun logout(): Flow<Result<ResponseMessage>> {
        return autoLoginDataSource.autoLoginInfo.flatMapLatest {
            callApi {
                apiService.postLogout(it.token)
            }
        }.map {
            when(it){
                is Result.Success -> {
                    Result.Success(
                        ResponseMessage(
                            status = it.data.hashCode(),
                            message = it.data,
                            code = it.data
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
    }

    override fun temporaryLogin(): Flow<Result<ResponseMessage>> = callApi {
        apiService.postLogin(
            StudentItem(
                "282838",
                "테스트",
                "01028282838"
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
}