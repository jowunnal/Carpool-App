package com.mate.carpool.data.module

import com.mate.carpool.data.repository.CarpoolListRepository
import com.mate.carpool.data.repository.CarpoolListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsCarpoolListRepository(carpoolListRepositoryImpl: CarpoolListRepositoryImpl):CarpoolListRepository
}