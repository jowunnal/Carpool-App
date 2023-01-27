package com.mate.carpool.data.repository.impl

import androidx.datastore.core.DataStore
import com.mate.carpool.AutoLoginPreferences
import com.mate.carpool.data.Result
import com.mate.carpool.data.model.response.ResponseMessage
import com.mate.carpool.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val autoLoginDataStore: DataStore<AutoLoginPreferences>,
) : AuthRepository {

    override val autoLoginInfo: Flow<AutoLoginPreferences> = autoLoginDataStore.data  // TODO 비밀번호 복호화
        .catch { exception ->
            if (exception is IOException) {
                emit(AutoLoginPreferences.getDefaultInstance())
            } else {
                throw exception
            }
        }

    override fun login(email: String, password: String) = flow {
        when {
            email == "fail" -> {
                emit(Result.Error("invalidEmail"))
            }

            password == "fail" -> {
                emit(Result.Error("invalidPassword"))
            }

            else -> {
                // TODO 토큰 저장
                updateAutoLoginInfo(email = email, password = password)
                emit(Result.Success(ResponseMessage()))
            }
        }
    }

    private suspend fun updateAutoLoginInfo(email: String, password: String) {
        autoLoginDataStore.updateData { preferences ->
            // TODO 비밀번호 암호화
            preferences.toBuilder().setEmail(email).setPassword(password).build()
        }
    }
}