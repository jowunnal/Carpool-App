package com.mate.carpool.di.module

import android.content.Context
import androidx.datastore.core.DataStore
import com.mate.carpool.AutoLoginPreferences
import com.mate.carpool.data.datastore.autoLoginPreferencesStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    fun provideAutoDataStore(@ApplicationContext context: Context): DataStore<AutoLoginPreferences> {
        return context.autoLoginPreferencesStore
    }
}