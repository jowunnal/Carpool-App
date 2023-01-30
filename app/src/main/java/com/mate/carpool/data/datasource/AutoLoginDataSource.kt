package com.mate.carpool.data.datasource

import androidx.datastore.core.DataStore
import com.mate.carpool.AutoLoginPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import javax.inject.Inject

class AutoLoginDataSource @Inject constructor(
    private val autoLoginDataStore: DataStore<AutoLoginPreferences>
) {
    val autoLoginInfo: Flow<AutoLoginPreferences> =
        autoLoginDataStore.data  // TODO 비밀번호 복호화
            .catch { exception ->
                if (exception is IOException) {
                    emit(AutoLoginPreferences.getDefaultInstance())
                } else {
                    throw exception
                }
            }

    suspend fun updateAutoLoginInfo(token:String) {
        autoLoginDataStore.updateData { preferences ->
            // TODO 토큰암호화? 필요한가?
            preferences.toBuilder().setToken(token).build()
        }
    }
}