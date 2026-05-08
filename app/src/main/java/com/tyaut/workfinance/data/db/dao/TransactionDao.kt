package com.tyaut.workfinance.data.db.dao

import androidx.room.*
import com.tyaut.workfinance.data.db.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions WHERE transactionDate BETWEEN :fromEpochDay AND :toEpochDay ORDER BY transactionDate DESC")
    fun observeByDateRange(fromEpochDay: Long, toEpochDay: Long): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE accountId = :accountId ORDER BY transactionDate DESC")
    fun observeByAccount(accountId: Long): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE creditCardPaymentMonth = :yearMonth AND isConfirmed = 0")
    fun observeUnconfirmedByPaymentMonth(yearMonth: String): Flow<List<TransactionEntity>>

    /** 予測計算用: 未来のすべての未確定クレカ取引を取得 */
    @Query("SELECT * FROM transactions WHERE creditCardPaymentMonth >= :fromYearMonth AND type = 'EXPENSE'")
    suspend fun getFutureCardTransactions(fromYearMonth: String): List<TransactionEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(transaction: TransactionEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(transactions: List<TransactionEntity>)

    @Update
    suspend fun update(transaction: TransactionEntity)

    /** クレカ確定処理: 支払月の上書きと確定フラグを同時に更新 */
    @Query("""
        UPDATE transactions SET
            isConfirmed = 1,
            creditCardPaymentMonth = :paymentMonth,
            updatedAt = :now
        WHERE id = :id
    """)
    suspend fun confirmCreditCardTransaction(id: Long, paymentMonth: String, now: Long = System.currentTimeMillis())

    /** 赤黒処理: 削除せず論理削除も行わない — マイナス支出で相殺する設計 */
    @Query("SELECT SUM(amount) FROM transactions WHERE accountId = :accountId AND transactionDate <= :upToEpochDay")
    suspend fun sumAmountUpTo(accountId: Long, upToEpochDay: Long): Long?

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun findById(id: Long): TransactionEntity?

    @Query("DELETE FROM transactions WHERE transactionDate BETWEEN :fromEpochDay AND :toEpochDay")
    suspend fun deleteByDateRange(fromEpochDay: Long, toEpochDay: Long)
}
