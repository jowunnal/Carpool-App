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
        autoLoginDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(AutoLoginPreferences.getDefaultInstance())
                } else {
                    throw exception
                }
            }

    suspend fun updateAutoLoginInfo(
        accessToken: String,
        refreshToken: String
    ) {
        autoLoginDataStore.updateData { preferences ->
            // TODO 토큰암호화? 필요한가?
            preferences.toBuilder()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .build()
        }
    }
}