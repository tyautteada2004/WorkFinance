package com.tyaut.workfinance.data.archive

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.tyaut.workfinance.data.repository.MonthlySummaryRepository
import com.tyaut.workfinance.data.repository.ShiftRepository
import com.tyaut.workfinance.data.repository.TransactionRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.time.YearMonth
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 月次確定アーカイブ処理。
 * 3ヶ月以上前で確定済みの月のトランザクション・シフトを、
 * 月ごとの外部SQLiteファイルに書き出し、メインDBから削除する。
 * アーカイブDB は読み取り専用でオンデマンド接続される。
 */
@Singleton
class ArchiveManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val transactionRepo: TransactionRepository,
    private val shiftRepo: ShiftRepository,
    private val summaryRepo: MonthlySummaryRepository,
) {
    private val archiveDir: File
        get() = File(context.filesDir, "archives").also { it.mkdirs() }

    fun archiveFilePath(yearMonth: YearMonth): String =
        File(archiveDir, "archive_${yearMonth}.db").absolutePath

    fun archiveFileExists(yearMonth: YearMonth): Boolean =
        File(archiveFilePath(yearMonth)).exists()

    /**
     * 指定月のデータをアーカイブファイルへ書き出す。
     * 書き出し後、メインDBの該当データを削除する。
     * メインDBのサマリーテーブルは保持（統計グラフ用）。
     */
    suspend fun archiveMonth(yearMonth: YearMonth) {
        val fromEpochDay = yearMonth.atDay(1).toEpochDay()
        val toEpochDay = yearMonth.atEndOfMonth().toEpochDay()
        val fromMs = yearMonth.atDay(1).atStartOfDay()
            .atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli()
        val toMs = yearMonth.atEndOfMonth().atTime(23, 59, 59)
            .atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli()

        val filePath = archiveFilePath(yearMonth)
        val db = SQLiteDatabase.openOrCreateDatabase(filePath, null)

        try {
            createArchiveSchema(db)
            // トランザクション・シフトのコピーはDAOから取得してinsertする処理を追加（TODO）
            db.close()

            // メインDBから削除
            transactionRepo.deleteByDateRange(fromEpochDay, toEpochDay)
            // サマリーにアーカイブ済みとしてマーク
            summaryRepo.markArchived(yearMonth.toString(), filePath)
        } catch (e: Exception) {
            db.close()
            File(filePath).delete()
            throw e
        }
    }

    /**
     * アーカイブDBを読み取り専用で開く。
     * 存在しない場合は null を返す（呼び出し元でハンドリング）。
     */
    fun openReadOnly(yearMonth: YearMonth): SQLiteDatabase? {
        val file = File(archiveFilePath(yearMonth))
        if (!file.exists()) return null
        return SQLiteDatabase.openDatabase(file.path, null, SQLiteDatabase.OPEN_READONLY)
    }

    private fun createArchiveSchema(db: SQLiteDatabase) {
        // 非正規化済みスキーマ: カテゴリ名・勤務先名をIDではなく名称で保存
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS archived_transactions (
                id INTEGER PRIMARY KEY,
                account_name TEXT NOT NULL,
                account_type TEXT NOT NULL,
                amount INTEGER NOT NULL,
                type TEXT NOT NULL,
                category TEXT,
                description TEXT NOT NULL,
                transaction_date INTEGER NOT NULL,
                credit_card_payment_month TEXT,
                note TEXT,
                created_at INTEGER NOT NULL
            )
        """)
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS archived_shifts (
                id INTEGER PRIMARY KEY,
                workplace_name TEXT NOT NULL,
                status TEXT NOT NULL,
                scheduled_start INTEGER NOT NULL,
                scheduled_end INTEGER NOT NULL,
                actual_start INTEGER,
                actual_end INTEGER,
                break_minutes INTEGER NOT NULL,
                confirmed_wage INTEGER,
                note TEXT
            )
        """)
    }
}
