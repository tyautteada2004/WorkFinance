package com.tyaut.workfinance.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.tyaut.workfinance.domain.enums.BusinessDayRule
import com.tyaut.workfinance.domain.enums.RoundingRule
import com.tyaut.workfinance.domain.enums.WageType

/**
 * 勤務先マスタ。WageType を Enum で保持し、将来の月給制拡張に対応。
 */
@Entity(
    tableName = "workplaces",
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["id"],
            childColumns = ["depositAccountId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index("depositAccountId")]
)
data class WorkplaceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val wageType: WageType = WageType.HOURLY,
    /** 時給制: 円/時 */
    val hourlyWage: Long? = null,
    /** 月給制: 円/月 */
    val monthlyWage: Long? = null,
    /** 深夜割増率（例: 1.25） */
    val nightPremiumRate: Double = 1.25,
    val roundingRule: RoundingRule = RoundingRule.FLOOR,
    val commissionEnabled: Boolean = false,
    /** 給料日 */
    val payDay: Int = 25,
    val payDayBusinessDayRule: BusinessDayRule? = null,
    /** 振込先口座ID */
    val depositAccountId: Long,
    /** 出勤前通知: 何分前（0=通知なし） */
    val preWorkNotifyMinutes: Int = 30,
    val isActive: Boolean = true
)
