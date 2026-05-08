package com.tyaut.workfinance.data.db.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.tyaut.workfinance.data.db.Converters;
import com.tyaut.workfinance.data.db.entity.WorkplaceEntity;
import com.tyaut.workfinance.domain.enums.BusinessDayRule;
import com.tyaut.workfinance.domain.enums.RoundingRule;
import com.tyaut.workfinance.domain.enums.WageType;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WorkplaceDao_Impl implements WorkplaceDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WorkplaceEntity> __insertionAdapterOfWorkplaceEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<WorkplaceEntity> __updateAdapterOfWorkplaceEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeactivate;

  public WorkplaceDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWorkplaceEntity = new EntityInsertionAdapter<WorkplaceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `workplaces` (`id`,`name`,`wageType`,`hourlyWage`,`monthlyWage`,`nightPremiumRate`,`roundingRule`,`commissionEnabled`,`payDay`,`payDayBusinessDayRule`,`depositAccountId`,`preWorkNotifyMinutes`,`isActive`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WorkplaceEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        final String _tmp = __converters.wageTypeToString(entity.getWageType());
        statement.bindString(3, _tmp);
        if (entity.getHourlyWage() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getHourlyWage());
        }
        if (entity.getMonthlyWage() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getMonthlyWage());
        }
        statement.bindDouble(6, entity.getNightPremiumRate());
        final String _tmp_1 = __converters.roundingRuleToString(entity.getRoundingRule());
        statement.bindString(7, _tmp_1);
        final int _tmp_2 = entity.getCommissionEnabled() ? 1 : 0;
        statement.bindLong(8, _tmp_2);
        statement.bindLong(9, entity.getPayDay());
        final String _tmp_3 = __converters.businessDayRuleToString(entity.getPayDayBusinessDayRule());
        if (_tmp_3 == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp_3);
        }
        statement.bindLong(11, entity.getDepositAccountId());
        statement.bindLong(12, entity.getPreWorkNotifyMinutes());
        final int _tmp_4 = entity.isActive() ? 1 : 0;
        statement.bindLong(13, _tmp_4);
      }
    };
    this.__updateAdapterOfWorkplaceEntity = new EntityDeletionOrUpdateAdapter<WorkplaceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `workplaces` SET `id` = ?,`name` = ?,`wageType` = ?,`hourlyWage` = ?,`monthlyWage` = ?,`nightPremiumRate` = ?,`roundingRule` = ?,`commissionEnabled` = ?,`payDay` = ?,`payDayBusinessDayRule` = ?,`depositAccountId` = ?,`preWorkNotifyMinutes` = ?,`isActive` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WorkplaceEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        final String _tmp = __converters.wageTypeToString(entity.getWageType());
        statement.bindString(3, _tmp);
        if (entity.getHourlyWage() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getHourlyWage());
        }
        if (entity.getMonthlyWage() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getMonthlyWage());
        }
        statement.bindDouble(6, entity.getNightPremiumRate());
        final String _tmp_1 = __converters.roundingRuleToString(entity.getRoundingRule());
        statement.bindString(7, _tmp_1);
        final int _tmp_2 = entity.getCommissionEnabled() ? 1 : 0;
        statement.bindLong(8, _tmp_2);
        statement.bindLong(9, entity.getPayDay());
        final String _tmp_3 = __converters.businessDayRuleToString(entity.getPayDayBusinessDayRule());
        if (_tmp_3 == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp_3);
        }
        statement.bindLong(11, entity.getDepositAccountId());
        statement.bindLong(12, entity.getPreWorkNotifyMinutes());
        final int _tmp_4 = entity.isActive() ? 1 : 0;
        statement.bindLong(13, _tmp_4);
        statement.bindLong(14, entity.getId());
      }
    };
    this.__preparedStmtOfDeactivate = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE workplaces SET isActive = 0 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final WorkplaceEntity workplace,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfWorkplaceEntity.insertAndReturnId(workplace);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final WorkplaceEntity workplace,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWorkplaceEntity.handle(workplace);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deactivate(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeactivate.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeactivate.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<WorkplaceEntity>> observeAll() {
    final String _sql = "SELECT * FROM workplaces WHERE isActive = 1 ORDER BY name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"workplaces"}, new Callable<List<WorkplaceEntity>>() {
      @Override
      @NonNull
      public List<WorkplaceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfWageType = CursorUtil.getColumnIndexOrThrow(_cursor, "wageType");
          final int _cursorIndexOfHourlyWage = CursorUtil.getColumnIndexOrThrow(_cursor, "hourlyWage");
          final int _cursorIndexOfMonthlyWage = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyWage");
          final int _cursorIndexOfNightPremiumRate = CursorUtil.getColumnIndexOrThrow(_cursor, "nightPremiumRate");
          final int _cursorIndexOfRoundingRule = CursorUtil.getColumnIndexOrThrow(_cursor, "roundingRule");
          final int _cursorIndexOfCommissionEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "commissionEnabled");
          final int _cursorIndexOfPayDay = CursorUtil.getColumnIndexOrThrow(_cursor, "payDay");
          final int _cursorIndexOfPayDayBusinessDayRule = CursorUtil.getColumnIndexOrThrow(_cursor, "payDayBusinessDayRule");
          final int _cursorIndexOfDepositAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "depositAccountId");
          final int _cursorIndexOfPreWorkNotifyMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "preWorkNotifyMinutes");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final List<WorkplaceEntity> _result = new ArrayList<WorkplaceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WorkplaceEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final WageType _tmpWageType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfWageType);
            _tmpWageType = __converters.stringToWageType(_tmp);
            final Long _tmpHourlyWage;
            if (_cursor.isNull(_cursorIndexOfHourlyWage)) {
              _tmpHourlyWage = null;
            } else {
              _tmpHourlyWage = _cursor.getLong(_cursorIndexOfHourlyWage);
            }
            final Long _tmpMonthlyWage;
            if (_cursor.isNull(_cursorIndexOfMonthlyWage)) {
              _tmpMonthlyWage = null;
            } else {
              _tmpMonthlyWage = _cursor.getLong(_cursorIndexOfMonthlyWage);
            }
            final double _tmpNightPremiumRate;
            _tmpNightPremiumRate = _cursor.getDouble(_cursorIndexOfNightPremiumRate);
            final RoundingRule _tmpRoundingRule;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfRoundingRule);
            _tmpRoundingRule = __converters.stringToRoundingRule(_tmp_1);
            final boolean _tmpCommissionEnabled;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfCommissionEnabled);
            _tmpCommissionEnabled = _tmp_2 != 0;
            final int _tmpPayDay;
            _tmpPayDay = _cursor.getInt(_cursorIndexOfPayDay);
            final BusinessDayRule _tmpPayDayBusinessDayRule;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfPayDayBusinessDayRule)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfPayDayBusinessDayRule);
            }
            _tmpPayDayBusinessDayRule = __converters.stringToBusinessDayRule(_tmp_3);
            final long _tmpDepositAccountId;
            _tmpDepositAccountId = _cursor.getLong(_cursorIndexOfDepositAccountId);
            final int _tmpPreWorkNotifyMinutes;
            _tmpPreWorkNotifyMinutes = _cursor.getInt(_cursorIndexOfPreWorkNotifyMinutes);
            final boolean _tmpIsActive;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_4 != 0;
            _item = new WorkplaceEntity(_tmpId,_tmpName,_tmpWageType,_tmpHourlyWage,_tmpMonthlyWage,_tmpNightPremiumRate,_tmpRoundingRule,_tmpCommissionEnabled,_tmpPayDay,_tmpPayDayBusinessDayRule,_tmpDepositAccountId,_tmpPreWorkNotifyMinutes,_tmpIsActive);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object findById(final long id, final Continuation<? super WorkplaceEntity> $completion) {
    final String _sql = "SELECT * FROM workplaces WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<WorkplaceEntity>() {
      @Override
      @Nullable
      public WorkplaceEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfWageType = CursorUtil.getColumnIndexOrThrow(_cursor, "wageType");
          final int _cursorIndexOfHourlyWage = CursorUtil.getColumnIndexOrThrow(_cursor, "hourlyWage");
          final int _cursorIndexOfMonthlyWage = CursorUtil.getColumnIndexOrThrow(_cursor, "monthlyWage");
          final int _cursorIndexOfNightPremiumRate = CursorUtil.getColumnIndexOrThrow(_cursor, "nightPremiumRate");
          final int _cursorIndexOfRoundingRule = CursorUtil.getColumnIndexOrThrow(_cursor, "roundingRule");
          final int _cursorIndexOfCommissionEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "commissionEnabled");
          final int _cursorIndexOfPayDay = CursorUtil.getColumnIndexOrThrow(_cursor, "payDay");
          final int _cursorIndexOfPayDayBusinessDayRule = CursorUtil.getColumnIndexOrThrow(_cursor, "payDayBusinessDayRule");
          final int _cursorIndexOfDepositAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "depositAccountId");
          final int _cursorIndexOfPreWorkNotifyMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "preWorkNotifyMinutes");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final WorkplaceEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final WageType _tmpWageType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfWageType);
            _tmpWageType = __converters.stringToWageType(_tmp);
            final Long _tmpHourlyWage;
            if (_cursor.isNull(_cursorIndexOfHourlyWage)) {
              _tmpHourlyWage = null;
            } else {
              _tmpHourlyWage = _cursor.getLong(_cursorIndexOfHourlyWage);
            }
            final Long _tmpMonthlyWage;
            if (_cursor.isNull(_cursorIndexOfMonthlyWage)) {
              _tmpMonthlyWage = null;
            } else {
              _tmpMonthlyWage = _cursor.getLong(_cursorIndexOfMonthlyWage);
            }
            final double _tmpNightPremiumRate;
            _tmpNightPremiumRate = _cursor.getDouble(_cursorIndexOfNightPremiumRate);
            final RoundingRule _tmpRoundingRule;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfRoundingRule);
            _tmpRoundingRule = __converters.stringToRoundingRule(_tmp_1);
            final boolean _tmpCommissionEnabled;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfCommissionEnabled);
            _tmpCommissionEnabled = _tmp_2 != 0;
            final int _tmpPayDay;
            _tmpPayDay = _cursor.getInt(_cursorIndexOfPayDay);
            final BusinessDayRule _tmpPayDayBusinessDayRule;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfPayDayBusinessDayRule)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfPayDayBusinessDayRule);
            }
            _tmpPayDayBusinessDayRule = __converters.stringToBusinessDayRule(_tmp_3);
            final long _tmpDepositAccountId;
            _tmpDepositAccountId = _cursor.getLong(_cursorIndexOfDepositAccountId);
            final int _tmpPreWorkNotifyMinutes;
            _tmpPreWorkNotifyMinutes = _cursor.getInt(_cursorIndexOfPreWorkNotifyMinutes);
            final boolean _tmpIsActive;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_4 != 0;
            _result = new WorkplaceEntity(_tmpId,_tmpName,_tmpWageType,_tmpHourlyWage,_tmpMonthlyWage,_tmpNightPremiumRate,_tmpRoundingRule,_tmpCommissionEnabled,_tmpPayDay,_tmpPayDayBusinessDayRule,_tmpDepositAccountId,_tmpPreWorkNotifyMinutes,_tmpIsActive);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
