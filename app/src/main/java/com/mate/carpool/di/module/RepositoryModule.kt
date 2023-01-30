package com.mate.carpool.di.module

import com.mate.carpool.data.repository.*
import com.mate.carpool.data.repository.impl.*
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
    abstract fun bindsCarpoolListRepository(carpoolListRepositoryImpl: CarpoolListRepositoryImpl): CarpoolListRepository

    @Binds
    @Singleton
    abstract fun bindsMemberRepository(memberRepositoryImpl: MemberRepositoryImpl): MemberRepository

    @Binds
    @Singleton
    abstract fun bindsPassengerRepository(passengerRepositoryImpl: TicketChangeRepositoryImpl) : TicketChangeRepository

    @Binds
    @Singleton
    abstract fun bindsReportRepository(impl: ReportRepositoryImpl): ReportRepository


    @Binds
    @Singleton
    abstract fun bindsAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}