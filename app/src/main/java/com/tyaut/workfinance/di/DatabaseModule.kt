package com.tyaut.workfinance.di

import android.content.Context
import androidx.room.Room
import com.tyaut.workfinance.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideAccountDao(db: AppDatabase) = db.accountDao()
    @Provides fun provideTransactionDao(db: AppDatabase) = db.transactionDao()
    @Provides fun provideShiftDao(db: AppDatabase) = db.shiftDao()
    @Provides fun provideWorkplaceDao(db: AppDatabase) = db.workplaceDao()
    @Provides fun provideFixedExpenseDao(db: AppDatabase) = db.fixedExpenseDao()
    @Provides fun provideMonthlySummaryDao(db: AppDatabase) = db.monthlySummaryDao()
}
