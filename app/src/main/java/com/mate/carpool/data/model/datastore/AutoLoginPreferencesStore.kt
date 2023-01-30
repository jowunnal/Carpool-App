package com.mate.carpool.data.model.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.mate.carpool.AutoLoginPreferences
import com.mate.carpool.data.serializer.AutoLoginPreferencesSerializer


private const val DATA_STORE_FILE_NAME = "auto_login_prefs.pb"

val Context.autoLoginPreferencesStore: DataStore<AutoLoginPreferences> by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = AutoLoginPreferencesSerializer
)
