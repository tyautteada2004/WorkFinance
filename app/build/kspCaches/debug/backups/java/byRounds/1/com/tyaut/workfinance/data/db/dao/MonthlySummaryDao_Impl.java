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
import com.tyaut.workfinance.data.db.entity.MonthlySummaryEntity;
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
public final class MonthlySummaryDao_Impl implements MonthlySummaryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MonthlySummaryEntity> __insertionAdapterOfMonthlySummaryEntity;

  private final SharedSQLiteStatement __preparedStmtOfMarkArchived;

  public MonthlySummaryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMonthlySummaryEntity = new EntityInsertionAdapter<MonthlySummaryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `monthly_summaries` (`id`,`yearMonth`,`totalIncome`,`totalExpense`,`totalWorkMinutes`,`totalConsumption`,`totalInvestment`,`totalWaste`,`isArchived`,`archiveFilePath`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MonthlySummaryEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getYearMonth());
        statement.bindLong(3, entity.getTotalIncome());
        statement.bindLong(4, entity.getTotalExpense());
        statement.bindLong(5, entity.getTotalWorkMinutes());
        statement.bindLong(6, entity.getTotalConsumption());
        statement.bindLong(7, entity.getTotalInvestment());
        statement.bindLong(8, entity.getTotalWaste());
        final int _tmp = entity.isArchived() ? 1 : 0;
        statement.bindLong(9, _tmp);
        if (entity.getArchiveFilePath() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getArchiveFilePath());
        }
      }
    };
    this.__preparedStmtOfMarkArchived = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE monthly_summaries SET\n"
                + "            isArchived = 1,\n"
                + "            archiveFilePath = ?\n"
                + "        WHERE yearMonth = ?\n"
                + "    ";
        return _query;
      }
    };
  }

  @Override
  public Object upsert(final MonthlySummaryEntity summary,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfMonthlySummaryEntity.insertAndReturnId(summary);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object markArchived(final String yearMonth, final String filePath,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkArchived.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, filePath);
        _argIndex = 2;
        _stmt.bindString(_argIndex, yearMonth);
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
          __preparedStmtOfMarkArchived.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<MonthlySummaryEntity>> observeAll() {
    final String _sql = "SELECT * FROM monthly_summaries ORDER BY yearMonth DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"monthly_summaries"}, new Callable<List<MonthlySummaryEntity>>() {
      @Override
      @NonNull
      public List<MonthlySummaryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
          final int _cursorIndexOfTotalIncome = CursorUtil.getColumnIndexOrThrow(_cursor, "totalIncome");
          final int _cursorIndexOfTotalExpense = CursorUtil.getColumnIndexOrThrow(_cursor, "totalExpense");
          final int _cursorIndexOfTotalWorkMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalWorkMinutes");
          final int _cursorIndexOfTotalConsumption = CursorUtil.getColumnIndexOrThrow(_cursor, "totalConsumption");
          final int _cursorIndexOfTotalInvestment = CursorUtil.getColumnIndexOrThrow(_cursor, "totalInvestment");
          final int _cursorIndexOfTotalWaste = CursorUtil.getColumnIndexOrThrow(_cursor, "totalWaste");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfArchiveFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "archiveFilePath");
          final List<MonthlySummaryEntity> _result = new ArrayList<MonthlySummaryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MonthlySummaryEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpYearMonth;
            _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
            final long _tmpTotalIncome;
            _tmpTotalIncome = _cursor.getLong(_cursorIndexOfTotalIncome);
            final long _tmpTotalExpense;
            _tmpTotalExpense = _cursor.getLong(_cursorIndexOfTotalExpense);
            final long _tmpTotalWorkMinutes;
            _tmpTotalWorkMinutes = _cursor.getLong(_cursorIndexOfTotalWorkMinutes);
            final long _tmpTotalConsumption;
            _tmpTotalConsumption = _cursor.getLong(_cursorIndexOfTotalConsumption);
            final long _tmpTotalInvestment;
            _tmpTotalInvestment = _cursor.getLong(_cursorIndexOfTotalInvestment);
            final long _tmpTotalWaste;
            _tmpTotalWaste = _cursor.getLong(_cursorIndexOfTotalWaste);
            final boolean _tmpIsArchived;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp != 0;
            final String _tmpArchiveFilePath;
            if (_cursor.isNull(_cursorIndexOfArchiveFilePath)) {
              _tmpArchiveFilePath = null;
            } else {
              _tmpArchiveFilePath = _cursor.getString(_cursorIndexOfArchiveFilePath);
            }
            _item = new MonthlySummaryEntity(_tmpId,_tmpYearMonth,_tmpTotalIncome,_tmpTotalExpense,_tmpTotalWorkMinutes,_tmpTotalConsumption,_tmpTotalInvestment,_tmpTotalWaste,_tmpIsArchived,_tmpArchiveFilePath);
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
  public Object findByMonth(final String yearMonth,
      final Continuation<? super MonthlySummaryEntity> $completion) {
    final String _sql = "SELECT * FROM monthly_summaries WHERE yearMonth = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, yearMonth);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<MonthlySummaryEntity>() {
      @Override
      @Nullable
      public MonthlySummaryEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
          final int _cursorIndexOfTotalIncome = CursorUtil.getColumnIndexOrThrow(_cursor, "totalIncome");
          final int _cursorIndexOfTotalExpense = CursorUtil.getColumnIndexOrThrow(_cursor, "totalExpense");
          final int _cursorIndexOfTotalWorkMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalWorkMinutes");
          final int _cursorIndexOfTotalConsumption = CursorUtil.getColumnIndexOrThrow(_cursor, "totalConsumption");
          final int _cursorIndexOfTotalInvestment = CursorUtil.getColumnIndexOrThrow(_cursor, "totalInvestment");
          final int _cursorIndexOfTotalWaste = CursorUtil.getColumnIndexOrThrow(_cursor, "totalWaste");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfArchiveFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "archiveFilePath");
          final MonthlySummaryEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpYearMonth;
            _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
            final long _tmpTotalIncome;
            _tmpTotalIncome = _cursor.getLong(_cursorIndexOfTotalIncome);
            final long _tmpTotalExpense;
            _tmpTotalExpense = _cursor.getLong(_cursorIndexOfTotalExpense);
            final long _tmpTotalWorkMinutes;
            _tmpTotalWorkMinutes = _cursor.getLong(_cursorIndexOfTotalWorkMinutes);
            final long _tmpTotalConsumption;
            _tmpTotalConsumption = _cursor.getLong(_cursorIndexOfTotalConsumption);
            final long _tmpTotalInvestment;
            _tmpTotalInvestment = _cursor.getLong(_cursorIndexOfTotalInvestment);
            final long _tmpTotalWaste;
            _tmpTotalWaste = _cursor.getLong(_cursorIndexOfTotalWaste);
            final boolean _tmpIsArchived;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp != 0;
            final String _tmpArchiveFilePath;
            if (_cursor.isNull(_cursorIndexOfArchiveFilePath)) {
              _tmpArchiveFilePath = null;
            } else {
              _tmpArchiveFilePath = _cursor.getString(_cursorIndexOfArchiveFilePath);
            }
            _result = new MonthlySummaryEntity(_tmpId,_tmpYearMonth,_tmpTotalIncome,_tmpTotalExpense,_tmpTotalWorkMinutes,_tmpTotalConsumption,_tmpTotalInvestment,_tmpTotalWaste,_tmpIsArchived,_tmpArchiveFilePath);
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
  public Flow<List<MonthlySummaryEntity>> observeRange(final String from, final String to) {
    final String _sql = "SELECT * FROM monthly_summaries WHERE yearMonth BETWEEN ? AND ? ORDER BY yearMonth";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, from);
    _argIndex = 2;
    _statement.bindString(_argIndex, to);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"monthly_summaries"}, new Callable<List<MonthlySummaryEntity>>() {
      @Override
      @NonNull
      public List<MonthlySummaryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
          final int _cursorIndexOfTotalIncome = CursorUtil.getColumnIndexOrThrow(_cursor, "totalIncome");
          final int _cursorIndexOfTotalExpense = CursorUtil.getColumnIndexOrThrow(_cursor, "totalExpense");
          final int _cursorIndexOfTotalWorkMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalWorkMinutes");
          final int _cursorIndexOfTotalConsumption = CursorUtil.getColumnIndexOrThrow(_cursor, "totalConsumption");
          final int _cursorIndexOfTotalInvestment = CursorUtil.getColumnIndexOrThrow(_cursor, "totalInvestment");
          final int _cursorIndexOfTotalWaste = CursorUtil.getColumnIndexOrThrow(_cursor, "totalWaste");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfArchiveFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "archiveFilePath");
          final List<MonthlySummaryEntity> _result = new ArrayList<MonthlySummaryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MonthlySummaryEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpYearMonth;
            _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
            final long _tmpTotalIncome;
            _tmpTotalIncome = _cursor.getLong(_cursorIndexOfTotalIncome);
            final long _tmpTotalExpense;
            _tmpTotalExpense = _cursor.getLong(_cursorIndexOfTotalExpense);
            final long _tmpTotalWorkMinutes;
            _tmpTotalWorkMinutes = _cursor.getLong(_cursorIndexOfTotalWorkMinutes);
            final long _tmpTotalConsumption;
            _tmpTotalConsumption = _cursor.getLong(_cursorIndexOfTotalConsumption);
            final long _tmpTotalInvestment;
            _tmpTotalInvestment = _cursor.getLong(_cursorIndexOfTotalInvestment);
            final long _tmpTotalWaste;
            _tmpTotalWaste = _cursor.getLong(_cursorIndexOfTotalWaste);
            final boolean _tmpIsArchived;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp != 0;
            final String _tmpArchiveFilePath;
            if (_cursor.isNull(_cursorIndexOfArchiveFilePath)) {
              _tmpArchiveFilePath = null;
            } else {
              _tmpArchiveFilePath = _cursor.getString(_cursorIndexOfArchiveFilePath);
            }
            _item = new MonthlySummaryEntity(_tmpId,_tmpYearMonth,_tmpTotalIncome,_tmpTotalExpense,_tmpTotalWorkMinutes,_tmpTotalConsumption,_tmpTotalInvestment,_tmpTotalWaste,_tmpIsArchived,_tmpArchiveFilePath);
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
