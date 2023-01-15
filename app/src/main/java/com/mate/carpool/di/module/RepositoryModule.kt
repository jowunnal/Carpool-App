package com.mate.carpool.di.module

import com.mate.carpool.data.repository.*
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
    abstract fun bindsCarpoolListRepository(carpoolListRepositoryImpl: CarpoolListRepositoryImpl) : CarpoolListRepository

    @Binds
    @Singleton
    abstract fun bindsMemberRepository(memberRepositoryImpl: MemberRepositoryImpl) : MemberRepository

    @Binds
    @Singleton
    abstract fun bindsPassengerRepository(passengerRepositoryImpl: PassengerRepositoryImpl) : PassengerRepository

}