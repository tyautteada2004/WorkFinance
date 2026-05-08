package com.tyaut.workfinance.data.db.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.tyaut.workfinance.data.db.Converters;
import com.tyaut.workfinance.data.db.entity.AccountEntity;
import com.tyaut.workfinance.domain.enums.AccountType;
import com.tyaut.workfinance.domain.enums.BusinessDayRule;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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
public final class AccountDao_Impl implements AccountDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AccountEntity> __insertionAdapterOfAccountEntity;

  private final Converters __converters = new Converters();

  private final SharedSQLiteStatement __preparedStmtOfUpdateSettings;

  private final SharedSQLiteStatement __preparedStmtOfDeactivate;

  public AccountDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAccountEntity = new EntityInsertionAdapter<AccountEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `accounts` (`id`,`name`,`type`,`initialBalance`,`closingDay`,`paymentDay`,`businessDayRule`,`isActive`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AccountEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        final String _tmp = __converters.accountTypeToString(entity.getType());
        statement.bindString(3, _tmp);
        statement.bindLong(4, entity.getInitialBalance());
        if (entity.getClosingDay() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getClosingDay());
        }
        if (entity.getPaymentDay() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getPaymentDay());
        }
        final String _tmp_1 = __converters.businessDayRuleToString(entity.getBusinessDayRule());
        if (_tmp_1 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_1);
        }
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(8, _tmp_2);
        statement.bindLong(9, entity.getCreatedAt());
      }
    };
    this.__preparedStmtOfUpdateSettings = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE accounts SET\n"
                + "            name = ?,\n"
                + "            closingDay = ?,\n"
                + "            paymentDay = ?,\n"
                + "            businessDayRule = ?,\n"
                + "            isActive = ?\n"
                + "        WHERE id = ?\n"
                + "    ";
        return _query;
      }
    };
    this.__preparedStmtOfDeactivate = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE accounts SET isActive = 0 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final AccountEntity account, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfAccountEntity.insertAndReturnId(account);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSettings(final long id, final String name, final Integer closingDay,
      final Integer paymentDay, final String businessDayRule, final boolean isActive,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSettings.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, name);
        _argIndex = 2;
        if (closingDay == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, closingDay);
        }
        _argIndex = 3;
        if (paymentDay == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, paymentDay);
        }
        _argIndex = 4;
        if (businessDayRule == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, businessDayRule);
        }
        _argIndex = 5;
        final int _tmp = isActive ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 6;
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
          __preparedStmtOfUpdateSettings.release(_stmt);
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
  public Flow<List<AccountEntity>> observeAll() {
    final String _sql = "SELECT * FROM accounts WHERE isActive = 1 ORDER BY type, name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"accounts"}, new Callable<List<AccountEntity>>() {
      @Override
      @NonNull
      public List<AccountEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfInitialBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "initialBalance");
          final int _cursorIndexOfClosingDay = CursorUtil.getColumnIndexOrThrow(_cursor, "closingDay");
          final int _cursorIndexOfPaymentDay = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentDay");
          final int _cursorIndexOfBusinessDayRule = CursorUtil.getColumnIndexOrThrow(_cursor, "businessDayRule");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<AccountEntity> _result = new ArrayList<AccountEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AccountEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final AccountType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.stringToAccountType(_tmp);
            final long _tmpInitialBalance;
            _tmpInitialBalance = _cursor.getLong(_cursorIndexOfInitialBalance);
            final Integer _tmpClosingDay;
            if (_cursor.isNull(_cursorIndexOfClosingDay)) {
              _tmpClosingDay = null;
            } else {
              _tmpClosingDay = _cursor.getInt(_cursorIndexOfClosingDay);
            }
            final Integer _tmpPaymentDay;
            if (_cursor.isNull(_cursorIndexOfPaymentDay)) {
              _tmpPaymentDay = null;
            } else {
              _tmpPaymentDay = _cursor.getInt(_cursorIndexOfPaymentDay);
            }
            final BusinessDayRule _tmpBusinessDayRule;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfBusinessDayRule)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfBusinessDayRule);
            }
            _tmpBusinessDayRule = __converters.stringToBusinessDayRule(_tmp_1);
            final boolean _tmpIsActive;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_2 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new AccountEntity(_tmpId,_tmpName,_tmpType,_tmpInitialBalance,_tmpClosingDay,_tmpPaymentDay,_tmpBusinessDayRule,_tmpIsActive,_tmpCreatedAt);
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
  public Object findById(final long id, final Continuation<? super AccountEntity> $completion) {
    final String _sql = "SELECT * FROM accounts WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<AccountEntity>() {
      @Override
      @Nullable
      public AccountEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfInitialBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "initialBalance");
          final int _cursorIndexOfClosingDay = CursorUtil.getColumnIndexOrThrow(_cursor, "closingDay");
          final int _cursorIndexOfPaymentDay = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentDay");
          final int _cursorIndexOfBusinessDayRule = CursorUtil.getColumnIndexOrThrow(_cursor, "businessDayRule");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final AccountEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final AccountType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.stringToAccountType(_tmp);
            final long _tmpInitialBalance;
            _tmpInitialBalance = _cursor.getLong(_cursorIndexOfInitialBalance);
            final Integer _tmpClosingDay;
            if (_cursor.isNull(_cursorIndexOfClosingDay)) {
              _tmpClosingDay = null;
            } else {
              _tmpClosingDay = _cursor.getInt(_cursorIndexOfClosingDay);
            }
            final Integer _tmpPaymentDay;
            if (_cursor.isNull(_cursorIndexOfPaymentDay)) {
              _tmpPaymentDay = null;
            } else {
              _tmpPaymentDay = _cursor.getInt(_cursorIndexOfPaymentDay);
            }
            final BusinessDayRule _tmpBusinessDayRule;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfBusinessDayRule)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfBusinessDayRule);
            }
            _tmpBusinessDayRule = __converters.stringToBusinessDayRule(_tmp_1);
            final boolean _tmpIsActive;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_2 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new AccountEntity(_tmpId,_tmpName,_tmpType,_tmpInitialBalance,_tmpClosingDay,_tmpPaymentDay,_tmpBusinessDayRule,_tmpIsActive,_tmpCreatedAt);
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
  public Flow<List<AccountEntity>> observeByType(final AccountType type) {
    final String _sql = "SELECT * FROM accounts WHERE type = ? AND isActive = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.accountTypeToString(type);
    _statement.bindString(_argIndex, _tmp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"accounts"}, new Callable<List<AccountEntity>>() {
      @Override
      @NonNull
      public List<AccountEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfInitialBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "initialBalance");
          final int _cursorIndexOfClosingDay = CursorUtil.getColumnIndexOrThrow(_cursor, "closingDay");
          final int _cursorIndexOfPaymentDay = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentDay");
          final int _cursorIndexOfBusinessDayRule = CursorUtil.getColumnIndexOrThrow(_cursor, "businessDayRule");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<AccountEntity> _result = new ArrayList<AccountEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AccountEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final AccountType _tmpType;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.stringToAccountType(_tmp_1);
            final long _tmpInitialBalance;
            _tmpInitialBalance = _cursor.getLong(_cursorIndexOfInitialBalance);
            final Integer _tmpClosingDay;
            if (_cursor.isNull(_cursorIndexOfClosingDay)) {
              _tmpClosingDay = null;
            } else {
              _tmpClosingDay = _cursor.getInt(_cursorIndexOfClosingDay);
            }
            final Integer _tmpPaymentDay;
            if (_cursor.isNull(_cursorIndexOfPaymentDay)) {
              _tmpPaymentDay = null;
            } else {
              _tmpPaymentDay = _cursor.getInt(_cursorIndexOfPaymentDay);
            }
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
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new AccountEntity(_tmpId,_tmpName,_tmpType,_tmpInitialBalance,_tmpClosingDay,_tmpPaymentDay,_tmpBusinessDayRule,_tmpIsActive,_tmpCreatedAt);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
