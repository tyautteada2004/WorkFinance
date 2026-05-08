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
import com.tyaut.workfinance.data.db.entity.TransactionEntity;
import com.tyaut.workfinance.domain.enums.ExpenseCategory;
import com.tyaut.workfinance.domain.enums.TransactionType;
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
public final class TransactionDao_Impl implements TransactionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TransactionEntity> __insertionAdapterOfTransactionEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<TransactionEntity> __updateAdapterOfTransactionEntity;

  private final SharedSQLiteStatement __preparedStmtOfConfirmCreditCardTransaction;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByDateRange;

  public TransactionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTransactionEntity = new EntityInsertionAdapter<TransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `transactions` (`id`,`accountId`,`amount`,`type`,`category`,`description`,`transactionDate`,`isConfirmed`,`creditCardPaymentMonth`,`pairedTransactionId`,`isFromFixedExpense`,`fixedExpenseId`,`note`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TransactionEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getAccountId());
        statement.bindLong(3, entity.getAmount());
        final String _tmp = __converters.transactionTypeToString(entity.getType());
        statement.bindString(4, _tmp);
        final String _tmp_1 = __converters.expenseCategoryToString(entity.getCategory());
        if (_tmp_1 == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp_1);
        }
        statement.bindString(6, entity.getDescription());
        statement.bindLong(7, entity.getTransactionDate());
        final int _tmp_2 = entity.isConfirmed() ? 1 : 0;
        statement.bindLong(8, _tmp_2);
        if (entity.getCreditCardPaymentMonth() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getCreditCardPaymentMonth());
        }
        if (entity.getPairedTransactionId() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getPairedTransactionId());
        }
        final int _tmp_3 = entity.isFromFixedExpense() ? 1 : 0;
        statement.bindLong(11, _tmp_3);
        if (entity.getFixedExpenseId() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getFixedExpenseId());
        }
        if (entity.getNote() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getNote());
        }
        statement.bindLong(14, entity.getCreatedAt());
        statement.bindLong(15, entity.getUpdatedAt());
      }
    };
    this.__updateAdapterOfTransactionEntity = new EntityDeletionOrUpdateAdapter<TransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `transactions` SET `id` = ?,`accountId` = ?,`amount` = ?,`type` = ?,`category` = ?,`description` = ?,`transactionDate` = ?,`isConfirmed` = ?,`creditCardPaymentMonth` = ?,`pairedTransactionId` = ?,`isFromFixedExpense` = ?,`fixedExpenseId` = ?,`note` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TransactionEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getAccountId());
        statement.bindLong(3, entity.getAmount());
        final String _tmp = __converters.transactionTypeToString(entity.getType());
        statement.bindString(4, _tmp);
        final String _tmp_1 = __converters.expenseCategoryToString(entity.getCategory());
        if (_tmp_1 == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp_1);
        }
        statement.bindString(6, entity.getDescription());
        statement.bindLong(7, entity.getTransactionDate());
        final int _tmp_2 = entity.isConfirmed() ? 1 : 0;
        statement.bindLong(8, _tmp_2);
        if (entity.getCreditCardPaymentMonth() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getCreditCardPaymentMonth());
        }
        if (entity.getPairedTransactionId() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getPairedTransactionId());
        }
        final int _tmp_3 = entity.isFromFixedExpense() ? 1 : 0;
        statement.bindLong(11, _tmp_3);
        if (entity.getFixedExpenseId() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getFixedExpenseId());
        }
        if (entity.getNote() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getNote());
        }
        statement.bindLong(14, entity.getCreatedAt());
        statement.bindLong(15, entity.getUpdatedAt());
        statement.bindLong(16, entity.getId());
      }
    };
    this.__preparedStmtOfConfirmCreditCardTransaction = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE transactions SET\n"
                + "            isConfirmed = 1,\n"
                + "            creditCardPaymentMonth = ?,\n"
                + "            updatedAt = ?\n"
                + "        WHERE id = ?\n"
                + "    ";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteByDateRange = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM transactions WHERE transactionDate BETWEEN ? AND ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final TransactionEntity transaction,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTransactionEntity.insertAndReturnId(transaction);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<TransactionEntity> transactions,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTransactionEntity.insert(transactions);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final TransactionEntity transaction,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTransactionEntity.handle(transaction);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object confirmCreditCardTransaction(final long id, final String paymentMonth,
      final long now, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfConfirmCreditCardTransaction.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, paymentMonth);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, now);
        _argIndex = 3;
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
          __preparedStmtOfConfirmCreditCardTransaction.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteByDateRange(final long fromEpochDay, final long toEpochDay,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByDateRange.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, fromEpochDay);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, toEpochDay);
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
          __preparedStmtOfDeleteByDateRange.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<TransactionEntity>> observeByDateRange(final long fromEpochDay,
      final long toEpochDay) {
    final String _sql = "SELECT * FROM transactions WHERE transactionDate BETWEEN ? AND ? ORDER BY transactionDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, fromEpochDay);
    _argIndex = 2;
    _statement.bindLong(_argIndex, toEpochDay);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transactions"}, new Callable<List<TransactionEntity>>() {
      @Override
      @NonNull
      public List<TransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTransactionDate = CursorUtil.getColumnIndexOrThrow(_cursor, "transactionDate");
          final int _cursorIndexOfIsConfirmed = CursorUtil.getColumnIndexOrThrow(_cursor, "isConfirmed");
          final int _cursorIndexOfCreditCardPaymentMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "creditCardPaymentMonth");
          final int _cursorIndexOfPairedTransactionId = CursorUtil.getColumnIndexOrThrow(_cursor, "pairedTransactionId");
          final int _cursorIndexOfIsFromFixedExpense = CursorUtil.getColumnIndexOrThrow(_cursor, "isFromFixedExpense");
          final int _cursorIndexOfFixedExpenseId = CursorUtil.getColumnIndexOrThrow(_cursor, "fixedExpenseId");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<TransactionEntity> _result = new ArrayList<TransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final TransactionType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.stringToTransactionType(_tmp);
            final ExpenseCategory _tmpCategory;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfCategory);
            }
            _tmpCategory = __converters.stringToExpenseCategory(_tmp_1);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final long _tmpTransactionDate;
            _tmpTransactionDate = _cursor.getLong(_cursorIndexOfTransactionDate);
            final boolean _tmpIsConfirmed;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsConfirmed);
            _tmpIsConfirmed = _tmp_2 != 0;
            final String _tmpCreditCardPaymentMonth;
            if (_cursor.isNull(_cursorIndexOfCreditCardPaymentMonth)) {
              _tmpCreditCardPaymentMonth = null;
            } else {
              _tmpCreditCardPaymentMonth = _cursor.getString(_cursorIndexOfCreditCardPaymentMonth);
            }
            final Long _tmpPairedTransactionId;
            if (_cursor.isNull(_cursorIndexOfPairedTransactionId)) {
              _tmpPairedTransactionId = null;
            } else {
              _tmpPairedTransactionId = _cursor.getLong(_cursorIndexOfPairedTransactionId);
            }
            final boolean _tmpIsFromFixedExpense;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsFromFixedExpense);
            _tmpIsFromFixedExpense = _tmp_3 != 0;
            final Long _tmpFixedExpenseId;
            if (_cursor.isNull(_cursorIndexOfFixedExpenseId)) {
              _tmpFixedExpenseId = null;
            } else {
              _tmpFixedExpenseId = _cursor.getLong(_cursorIndexOfFixedExpenseId);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new TransactionEntity(_tmpId,_tmpAccountId,_tmpAmount,_tmpType,_tmpCategory,_tmpDescription,_tmpTransactionDate,_tmpIsConfirmed,_tmpCreditCardPaymentMonth,_tmpPairedTransactionId,_tmpIsFromFixedExpense,_tmpFixedExpenseId,_tmpNote,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<TransactionEntity>> observeByAccount(final long accountId) {
    final String _sql = "SELECT * FROM transactions WHERE accountId = ? ORDER BY transactionDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, accountId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transactions"}, new Callable<List<TransactionEntity>>() {
      @Override
      @NonNull
      public List<TransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTransactionDate = CursorUtil.getColumnIndexOrThrow(_cursor, "transactionDate");
          final int _cursorIndexOfIsConfirmed = CursorUtil.getColumnIndexOrThrow(_cursor, "isConfirmed");
          final int _cursorIndexOfCreditCardPaymentMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "creditCardPaymentMonth");
          final int _cursorIndexOfPairedTransactionId = CursorUtil.getColumnIndexOrThrow(_cursor, "pairedTransactionId");
          final int _cursorIndexOfIsFromFixedExpense = CursorUtil.getColumnIndexOrThrow(_cursor, "isFromFixedExpense");
          final int _cursorIndexOfFixedExpenseId = CursorUtil.getColumnIndexOrThrow(_cursor, "fixedExpenseId");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<TransactionEntity> _result = new ArrayList<TransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final TransactionType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.stringToTransactionType(_tmp);
            final ExpenseCategory _tmpCategory;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfCategory);
            }
            _tmpCategory = __converters.stringToExpenseCategory(_tmp_1);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final long _tmpTransactionDate;
            _tmpTransactionDate = _cursor.getLong(_cursorIndexOfTransactionDate);
            final boolean _tmpIsConfirmed;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsConfirmed);
            _tmpIsConfirmed = _tmp_2 != 0;
            final String _tmpCreditCardPaymentMonth;
            if (_cursor.isNull(_cursorIndexOfCreditCardPaymentMonth)) {
              _tmpCreditCardPaymentMonth = null;
            } else {
              _tmpCreditCardPaymentMonth = _cursor.getString(_cursorIndexOfCreditCardPaymentMonth);
            }
            final Long _tmpPairedTransactionId;
            if (_cursor.isNull(_cursorIndexOfPairedTransactionId)) {
              _tmpPairedTransactionId = null;
            } else {
              _tmpPairedTransactionId = _cursor.getLong(_cursorIndexOfPairedTransactionId);
            }
            final boolean _tmpIsFromFixedExpense;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsFromFixedExpense);
            _tmpIsFromFixedExpense = _tmp_3 != 0;
            final Long _tmpFixedExpenseId;
            if (_cursor.isNull(_cursorIndexOfFixedExpenseId)) {
              _tmpFixedExpenseId = null;
            } else {
              _tmpFixedExpenseId = _cursor.getLong(_cursorIndexOfFixedExpenseId);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new TransactionEntity(_tmpId,_tmpAccountId,_tmpAmount,_tmpType,_tmpCategory,_tmpDescription,_tmpTransactionDate,_tmpIsConfirmed,_tmpCreditCardPaymentMonth,_tmpPairedTransactionId,_tmpIsFromFixedExpense,_tmpFixedExpenseId,_tmpNote,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<TransactionEntity>> observeUnconfirmedByPaymentMonth(final String yearMonth) {
    final String _sql = "SELECT * FROM transactions WHERE creditCardPaymentMonth = ? AND isConfirmed = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, yearMonth);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transactions"}, new Callable<List<TransactionEntity>>() {
      @Override
      @NonNull
      public List<TransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTransactionDate = CursorUtil.getColumnIndexOrThrow(_cursor, "transactionDate");
          final int _cursorIndexOfIsConfirmed = CursorUtil.getColumnIndexOrThrow(_cursor, "isConfirmed");
          final int _cursorIndexOfCreditCardPaymentMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "creditCardPaymentMonth");
          final int _cursorIndexOfPairedTransactionId = CursorUtil.getColumnIndexOrThrow(_cursor, "pairedTransactionId");
          final int _cursorIndexOfIsFromFixedExpense = CursorUtil.getColumnIndexOrThrow(_cursor, "isFromFixedExpense");
          final int _cursorIndexOfFixedExpenseId = CursorUtil.getColumnIndexOrThrow(_cursor, "fixedExpenseId");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<TransactionEntity> _result = new ArrayList<TransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final TransactionType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.stringToTransactionType(_tmp);
            final ExpenseCategory _tmpCategory;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfCategory);
            }
            _tmpCategory = __converters.stringToExpenseCategory(_tmp_1);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final long _tmpTransactionDate;
            _tmpTransactionDate = _cursor.getLong(_cursorIndexOfTransactionDate);
            final boolean _tmpIsConfirmed;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsConfirmed);
            _tmpIsConfirmed = _tmp_2 != 0;
            final String _tmpCreditCardPaymentMonth;
            if (_cursor.isNull(_cursorIndexOfCreditCardPaymentMonth)) {
              _tmpCreditCardPaymentMonth = null;
            } else {
              _tmpCreditCardPaymentMonth = _cursor.getString(_cursorIndexOfCreditCardPaymentMonth);
            }
            final Long _tmpPairedTransactionId;
            if (_cursor.isNull(_cursorIndexOfPairedTransactionId)) {
              _tmpPairedTransactionId = null;
            } else {
              _tmpPairedTransactionId = _cursor.getLong(_cursorIndexOfPairedTransactionId);
            }
            final boolean _tmpIsFromFixedExpense;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsFromFixedExpense);
            _tmpIsFromFixedExpense = _tmp_3 != 0;
            final Long _tmpFixedExpenseId;
            if (_cursor.isNull(_cursorIndexOfFixedExpenseId)) {
              _tmpFixedExpenseId = null;
            } else {
              _tmpFixedExpenseId = _cursor.getLong(_cursorIndexOfFixedExpenseId);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new TransactionEntity(_tmpId,_tmpAccountId,_tmpAmount,_tmpType,_tmpCategory,_tmpDescription,_tmpTransactionDate,_tmpIsConfirmed,_tmpCreditCardPaymentMonth,_tmpPairedTransactionId,_tmpIsFromFixedExpense,_tmpFixedExpenseId,_tmpNote,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getFutureCardTransactions(final String fromYearMonth,
      final Continuation<? super List<TransactionEntity>> $completion) {
    final String _sql = "SELECT * FROM transactions WHERE creditCardPaymentMonth >= ? AND type = 'EXPENSE'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, fromYearMonth);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TransactionEntity>>() {
      @Override
      @NonNull
      public List<TransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTransactionDate = CursorUtil.getColumnIndexOrThrow(_cursor, "transactionDate");
          final int _cursorIndexOfIsConfirmed = CursorUtil.getColumnIndexOrThrow(_cursor, "isConfirmed");
          final int _cursorIndexOfCreditCardPaymentMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "creditCardPaymentMonth");
          final int _cursorIndexOfPairedTransactionId = CursorUtil.getColumnIndexOrThrow(_cursor, "pairedTransactionId");
          final int _cursorIndexOfIsFromFixedExpense = CursorUtil.getColumnIndexOrThrow(_cursor, "isFromFixedExpense");
          final int _cursorIndexOfFixedExpenseId = CursorUtil.getColumnIndexOrThrow(_cursor, "fixedExpenseId");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<TransactionEntity> _result = new ArrayList<TransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final TransactionType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.stringToTransactionType(_tmp);
            final ExpenseCategory _tmpCategory;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfCategory);
            }
            _tmpCategory = __converters.stringToExpenseCategory(_tmp_1);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final long _tmpTransactionDate;
            _tmpTransactionDate = _cursor.getLong(_cursorIndexOfTransactionDate);
            final boolean _tmpIsConfirmed;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsConfirmed);
            _tmpIsConfirmed = _tmp_2 != 0;
            final String _tmpCreditCardPaymentMonth;
            if (_cursor.isNull(_cursorIndexOfCreditCardPaymentMonth)) {
              _tmpCreditCardPaymentMonth = null;
            } else {
              _tmpCreditCardPaymentMonth = _cursor.getString(_cursorIndexOfCreditCardPaymentMonth);
            }
            final Long _tmpPairedTransactionId;
            if (_cursor.isNull(_cursorIndexOfPairedTransactionId)) {
              _tmpPairedTransactionId = null;
            } else {
              _tmpPairedTransactionId = _cursor.getLong(_cursorIndexOfPairedTransactionId);
            }
            final boolean _tmpIsFromFixedExpense;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsFromFixedExpense);
            _tmpIsFromFixedExpense = _tmp_3 != 0;
            final Long _tmpFixedExpenseId;
            if (_cursor.isNull(_cursorIndexOfFixedExpenseId)) {
              _tmpFixedExpenseId = null;
            } else {
              _tmpFixedExpenseId = _cursor.getLong(_cursorIndexOfFixedExpenseId);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new TransactionEntity(_tmpId,_tmpAccountId,_tmpAmount,_tmpType,_tmpCategory,_tmpDescription,_tmpTransactionDate,_tmpIsConfirmed,_tmpCreditCardPaymentMonth,_tmpPairedTransactionId,_tmpIsFromFixedExpense,_tmpFixedExpenseId,_tmpNote,_tmpCreatedAt,_tmpUpdatedAt);
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

  @Override
  public Object sumAmountUpTo(final long accountId, final long upToEpochDay,
      final Continuation<? super Long> $completion) {
    final String _sql = "SELECT SUM(amount) FROM transactions WHERE accountId = ? AND transactionDate <= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, accountId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, upToEpochDay);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Long>() {
      @Override
      @Nullable
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            final Long _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(0);
            }
            _result = _tmp;
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
  public Object findById(final long id, final Continuation<? super TransactionEntity> $completion) {
    final String _sql = "SELECT * FROM transactions WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TransactionEntity>() {
      @Override
      @Nullable
      public TransactionEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTransactionDate = CursorUtil.getColumnIndexOrThrow(_cursor, "transactionDate");
          final int _cursorIndexOfIsConfirmed = CursorUtil.getColumnIndexOrThrow(_cursor, "isConfirmed");
          final int _cursorIndexOfCreditCardPaymentMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "creditCardPaymentMonth");
          final int _cursorIndexOfPairedTransactionId = CursorUtil.getColumnIndexOrThrow(_cursor, "pairedTransactionId");
          final int _cursorIndexOfIsFromFixedExpense = CursorUtil.getColumnIndexOrThrow(_cursor, "isFromFixedExpense");
          final int _cursorIndexOfFixedExpenseId = CursorUtil.getColumnIndexOrThrow(_cursor, "fixedExpenseId");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final TransactionEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final TransactionType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.stringToTransactionType(_tmp);
            final ExpenseCategory _tmpCategory;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfCategory);
            }
            _tmpCategory = __converters.stringToExpenseCategory(_tmp_1);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final long _tmpTransactionDate;
            _tmpTransactionDate = _cursor.getLong(_cursorIndexOfTransactionDate);
            final boolean _tmpIsConfirmed;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsConfirmed);
            _tmpIsConfirmed = _tmp_2 != 0;
            final String _tmpCreditCardPaymentMonth;
            if (_cursor.isNull(_cursorIndexOfCreditCardPaymentMonth)) {
              _tmpCreditCardPaymentMonth = null;
            } else {
              _tmpCreditCardPaymentMonth = _cursor.getString(_cursorIndexOfCreditCardPaymentMonth);
            }
            final Long _tmpPairedTransactionId;
            if (_cursor.isNull(_cursorIndexOfPairedTransactionId)) {
              _tmpPairedTransactionId = null;
            } else {
              _tmpPairedTransactionId = _cursor.getLong(_cursorIndexOfPairedTransactionId);
            }
            final boolean _tmpIsFromFixedExpense;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsFromFixedExpense);
            _tmpIsFromFixedExpense = _tmp_3 != 0;
            final Long _tmpFixedExpenseId;
            if (_cursor.isNull(_cursorIndexOfFixedExpenseId)) {
              _tmpFixedExpenseId = null;
            } else {
              _tmpFixedExpenseId = _cursor.getLong(_cursorIndexOfFixedExpenseId);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new TransactionEntity(_tmpId,_tmpAccountId,_tmpAmount,_tmpType,_tmpCategory,_tmpDescription,_tmpTransactionDate,_tmpIsConfirmed,_tmpCreditCardPaymentMonth,_tmpPairedTransactionId,_tmpIsFromFixedExpense,_tmpFixedExpenseId,_tmpNote,_tmpCreatedAt,_tmpUpdatedAt);
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
