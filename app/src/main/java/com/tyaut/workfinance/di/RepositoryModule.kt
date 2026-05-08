package com.tyaut.workfinance.di

import com.tyaut.workfinance.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton
    abstract fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository

    @Binds @Singleton
    abstract fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository

    @Binds @Singleton
    abstract fun bindShiftRepository(impl: ShiftRepositoryImpl): ShiftRepository

    @Binds @Singleton
    abstract fun bindWorkplaceRepository(impl: WorkplaceRepositoryImpl): WorkplaceRepository

    @Binds @Singleton
    abstract fun bindFixedExpenseRepository(impl: FixedExpenseRepositoryImpl): FixedExpenseRepository

    @Binds @Singleton
    abstract fun bindMonthlySummaryRepository(impl: MonthlySummaryRepositoryImpl): MonthlySummaryRepository
}
