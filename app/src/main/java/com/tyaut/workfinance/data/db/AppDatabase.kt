package com.tyaut.workfinance.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tyaut.workfinance.data.db.dao.*
import com.tyaut.workfinance.data.db.entity.*

@Database(
    entities = [
        AccountEntity::class,
        TransactionEntity::class,
        ShiftEntity::class,
        WorkplaceEntity::class,
        FixedExpenseEntity::class,
        MonthlySummaryEntity::class,
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
    abstract fun shiftDao(): ShiftDao
    abstract fun workplaceDao(): WorkplaceDao
    abstract fun fixedExpenseDao(): FixedExpenseDao
    abstract fun monthlySummaryDao(): MonthlySummaryDao

    companion object {
        const val DATABASE_NAME = "work_finance.db"
    }
}
