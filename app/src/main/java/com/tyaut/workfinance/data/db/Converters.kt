package com.tyaut.workfinance.data.db

import androidx.room.TypeConverter
import com.tyaut.workfinance.domain.enums.*

class Converters {

    @TypeConverter fun accountTypeToString(v: AccountType): String = v.name
    @TypeConverter fun stringToAccountType(v: String): AccountType = AccountType.valueOf(v)

    @TypeConverter fun expenseCategoryToString(v: ExpenseCategory?): String? = v?.name
    @TypeConverter fun stringToExpenseCategory(v: String?): ExpenseCategory? = v?.let { ExpenseCategory.valueOf(it) }

    @TypeConverter fun shiftStatusToString(v: ShiftStatus): String = v.name
    @TypeConverter fun stringToShiftStatus(v: String): ShiftStatus = ShiftStatus.valueOf(v)

    @TypeConverter fun wageTypeToString(v: WageType): String = v.name
    @TypeConverter fun stringToWageType(v: String): WageType = WageType.valueOf(v)

    @TypeConverter fun transactionTypeToString(v: TransactionType): String = v.name
    @TypeConverter fun stringToTransactionType(v: String): TransactionType = TransactionType.valueOf(v)

    @TypeConverter fun businessDayRuleToString(v: BusinessDayRule?): String? = v?.name
    @TypeConverter fun stringToBusinessDayRule(v: String?): BusinessDayRule? = v?.let { BusinessDayRule.valueOf(it) }

    @TypeConverter fun roundingRuleToString(v: RoundingRule): String = v.name
    @TypeConverter fun stringToRoundingRule(v: String): RoundingRule = RoundingRule.valueOf(v)
}
