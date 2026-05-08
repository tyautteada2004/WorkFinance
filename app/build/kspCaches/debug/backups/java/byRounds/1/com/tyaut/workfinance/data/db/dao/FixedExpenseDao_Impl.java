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
import com.tyaut.workfinance.data.db.entity.FixedExpenseEntity;
import com.tyaut.workfinance.domain.enums.BusinessDayRule;
import com.tyaut.workfinance.domain.enums.ExpenseCategory;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
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
public final class FixedExpenseDao_Impl implements FixedExpenseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FixedExpenseEntity> __insertionAdapterOfFixedExpenseEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<FixedExpenseEntity> __updateAdapterOfFixedExpenseEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeactivate;

  public FixedExpenseDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFixedExpenseEntity = new EntityInsertionAdapter<FixedExpenseEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `fixed_expenses` (`id`,`name`,`amount`,`category`,`accountId`,`accountName`,`startDate`,`endDate`,`dayOfMonth`,`businessDayRule`,`isActive`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FixedExpenseEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindLong(3, entity.getAmount());
        final String _tmp = __converters.expenseCategoryToString(entity.getCategory());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
        statement.bindLong(5, entity.getAccountId());
        statement.bindString(6, entity.getAccountName());
        statement.bindLong(7, entity.getStartDate());
        if (entity.getEndDate() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getEndDate());
        }
        statement.bindLong(9, entity.getDayOfMonth());
        final String _tmp_1 = __converters.businessDayRuleToString(entity.getBusinessDayRule());
        if (_tmp_1 == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp_1);
        }
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(11, _tmp_2);
      }
    };
    this.__updateAdapterOfFixedExpenseEntity = new EntityDeletionOrUpdateAdapter<FixedExpenseEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `fixed_expenses` SET `id` = ?,`name` = ?,`amount` = ?,`category` = ?,`accountId` = ?,`accountName` = ?,`startDate` = ?,`endDate` = ?,`dayOfMonth` = ?,`businessDayRule` = ?,`isActive` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FixedExpenseEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindLong(3, entity.getAmount());
        final String _tmp = __converters.expenseCategoryToString(entity.getCategory());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
        statement.bindLong(5, entity.getAccountId());
        statement.bindString(6, entity.getAccountName());
        statement.bindLong(7, entity.getStartDate());
        if (entity.getEndDate() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getEndDate());
        }
        statement.bindLong(9, entity.getDayOfMonth());
        final String _tmp_1 = __converters.businessDayRuleToString(entity.getBusinessDayRule());
        if (_tmp_1 == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp_1);
        }
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(11, _tmp_2);
        statement.bindLong(12, entity.getId());
      }
    };
    this.__preparedStmtOfDeactivate = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE fixed_expenses SET isActive = 0 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final FixedExpenseEntity fixedExpense,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfFixedExpenseEntity.insertAndReturnId(fixedExpense);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final FixedExpenseEntity fixedExpense,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfFixedExpenseEntity.handle(fixedExpense);
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
  public Flow<List<FixedExpenseEntity>> observeAll() {
    final String _sql = "SELECT * FROM fixed_expenses WHERE isActive = 1 ORDER BY name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"fixed_expenses"}, new Callable<List<FixedExpenseEntity>>() {
      @Override
      @NonNull
      public List<FixedExpenseEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfAccountName = CursorUtil.getColumnIndexOrThrow(_cursor, "accountName");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfDayOfMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfMonth");
          final int _cursorIndexOfBusinessDayRule = CursorUtil.getColumnIndexOrThrow(_cursor, "businessDayRule");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final List<FixedExpenseEntity> _result = new ArrayList<FixedExpenseEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FixedExpenseEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final ExpenseCategory _tmpCategory;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfCategory);
            }
            final ExpenseCategory _tmp_1 = __converters.stringToExpenseCategory(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.tyaut.workfinance.domain.enums.ExpenseCategory', but it was NULL.");
            } else {
              _tmpCategory = _tmp_1;
            }
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            final String _tmpAccountName;
            _tmpAccountName = _cursor.getString(_cursorIndexOfAccountName);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final Long _tmpEndDate;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmpEndDate = null;
            } else {
              _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            }
            final int _tmpDayOfMonth;
            _tmpDayOfMonth = _cursor.getInt(_cursorIndexOfDayOfMonth);
            final BusinessDayRule _tmpBusinessDayRule;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfBusinessDayRule)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfBusinessDayRule);
            }
            _tmpBusinessDayRule = __converters.stringToBusinessDayRule(_tmp_2);
            final boolean _tmpIsActive;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_3 != 0;
            _item = new FixedExpenseEntity(_tmpId,_tmpName,_tmpAmount,_tmpCategory,_tmpAccountId,_tmpAccountName,_tmpStartDate,_tmpEndDate,_tmpDayOfMonth,_tmpBusinessDayRule,_tmpIsActive);
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
  public Object findById(final long id,
      final Continuation<? super FixedExpenseEntity> $completion) {
    final String _sql = "SELECT * FROM fixed_expenses WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<FixedExpenseEntity>() {
      @Override
      @Nullable
      public FixedExpenseEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfAccountName = CursorUtil.getColumnIndexOrThrow(_cursor, "accountName");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfDayOfMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfMonth");
          final int _cursorIndexOfBusinessDayRule = CursorUtil.getColumnIndexOrThrow(_cursor, "businessDayRule");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final FixedExpenseEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final ExpenseCategory _tmpCategory;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfCategory);
            }
            final ExpenseCategory _tmp_1 = __converters.stringToExpenseCategory(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.tyaut.workfinance.domain.enums.ExpenseCategory', but it was NULL.");
            } else {
              _tmpCategory = _tmp_1;
            }
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            final String _tmpAccountName;
            _tmpAccountName = _cursor.getString(_cursorIndexOfAccountName);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final Long _tmpEndDate;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmpEndDate = null;
            } else {
              _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            }
            final int _tmpDayOfMonth;
            _tmpDayOfMonth = _cursor.getInt(_cursorIndexOfDayOfMonth);
            final BusinessDayRule _tmpBusinessDayRule;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfBusinessDayRule)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfBusinessDayRule);
            }
            _tmpBusinessDayRule = __converters.stringToBusinessDayRule(_tmp_2);
            final boolean _tmpIsActive;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_3 != 0;
            _result = new FixedExpenseEntity(_tmpId,_tmpName,_tmpAmount,_tmpCategory,_tmpAccountId,_tmpAccountName,_tmpStartDate,_tmpEndDate,_tmpDayOfMonth,_tmpBusinessDayRule,_tmpIsActive);
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

  @Override
  public Object getActiveOnDate(final long targetEpochDay,
      final Continuation<? super List<FixedExpenseEntity>> $completion) {
    final String _sql = "\n"
            + "        SELECT * FROM fixed_expenses\n"
            + "        WHERE isActive = 1\n"
            + "          AND startDate <= ?\n"
            + "          AND (endDate IS NULL OR endDate >= ?)\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, targetEpochDay);
    _argIndex = 2;
    _statement.bindLong(_argIndex, targetEpochDay);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FixedExpenseEntity>>() {
      @Override
      @NonNull
      public List<FixedExpenseEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfAccountName = CursorUtil.getColumnIndexOrThrow(_cursor, "accountName");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfDayOfMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfMonth");
          final int _cursorIndexOfBusinessDayRule = CursorUtil.getColumnIndexOrThrow(_cursor, "businessDayRule");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final List<FixedExpenseEntity> _result = new ArrayList<FixedExpenseEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FixedExpenseEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final ExpenseCategory _tmpCategory;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfCategory);
            }
            final ExpenseCategory _tmp_1 = __converters.stringToExpenseCategory(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.tyaut.workfinance.domain.enums.ExpenseCategory', but it was NULL.");
            } else {
              _tmpCategory = _tmp_1;
            }
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            final String _tmpAccountName;
            _tmpAccountName = _cursor.getString(_cursorIndexOfAccountName);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final Long _tmpEndDate;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmpEndDate = null;
            } else {
              _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            }
            final int _tmpDayOfMonth;
            _tmpDayOfMonth = _cursor.getInt(_cursorIndexOfDayOfMonth);
            final BusinessDayRule _tmpBusinessDayRule;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfBusinessDayRule)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfBusinessDayRule);
            }
            _tmpBusinessDayRule = __converters.stringToBusinessDayRule(_tmp_2);
            final boolean _tmpIsActive;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_3 != 0;
            _item = new FixedExpenseEntity(_tmpId,_tmpName,_tmpAmount,_tmpCategory,_tmpAccountId,_tmpAccountName,_tmpStartDate,_tmpEndDate,_tmpDayOfMonth,_tmpBusinessDayRule,_tmpIsActive);
            _result.add(_item);
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
