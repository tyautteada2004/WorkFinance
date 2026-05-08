package com.tyaut.workfinance.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.tyaut.workfinance.domain.enums.ShiftStatus

/**
 * シフト。3段階ステータス（予定→実績→確定）で管理。
 * workplaceName を非正規化保持し、アーカイブ後も名称が失われない設計。
 */
@Entity(
    tableName = "shifts",
    foreignKeys = [
        ForeignKey(
            entity = WorkplaceEntity::class,
            parentColumns = ["id"],
            childColumns = ["workplaceId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index("workplaceId"), Index("scheduledStart")]
)
data class ShiftEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val workplaceId: Long,
    /** 非正規化: アーカイブ後も名称保持 */
    val workplaceName: String,
    val status: ShiftStatus = ShiftStatus.SCHEDULED,
    /** エポックミリ秒 */
    val scheduledStart: Long,
    val scheduledEnd: Long,
    /** 実績入力後に設定 */
    val actualStart: Long? = null,
    val actualEnd: Long? = null,
    val breakMinutes: Int = 0,
    /** 予定時間ベースの計算賃金（予定時に算出） */
    val scheduledWage: Long? = null,
    /** 実働ベースの計算賃金（実績入力時に算出） */
    val actualWage: Long? = null,
    /** 税・端数調整後の確定賃金 */
    val confirmedWage: Long? = null,
    val googleCalendarEventId: String? = null,
    val note: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
