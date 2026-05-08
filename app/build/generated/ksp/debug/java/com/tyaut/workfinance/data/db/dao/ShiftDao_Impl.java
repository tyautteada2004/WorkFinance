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
import com.tyaut.workfinance.data.db.entity.ShiftEntity;
import com.tyaut.workfinance.domain.enums.ShiftStatus;
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
public final class ShiftDao_Impl implements ShiftDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ShiftEntity> __insertionAdapterOfShiftEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<ShiftEntity> __deletionAdapterOfShiftEntity;

  private final EntityDeletionOrUpdateAdapter<ShiftEntity> __updateAdapterOfShiftEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateActual;

  private final SharedSQLiteStatement __preparedStmtOfConfirm;

  private final SharedSQLiteStatement __preparedStmtOfUpdateCalendarEventId;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByDateRange;

  public ShiftDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfShiftEntity = new EntityInsertionAdapter<ShiftEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `shifts` (`id`,`workplaceId`,`workplaceName`,`status`,`scheduledStart`,`scheduledEnd`,`actualStart`,`actualEnd`,`breakMinutes`,`scheduledWage`,`actualWage`,`confirmedWage`,`googleCalendarEventId`,`note`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ShiftEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getWorkplaceId());
        statement.bindString(3, entity.getWorkplaceName());
        final String _tmp = __converters.shiftStatusToString(entity.getStatus());
        statement.bindString(4, _tmp);
        statement.bindLong(5, entity.getScheduledStart());
        statement.bindLong(6, entity.getScheduledEnd());
        if (entity.getActualStart() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getActualStart());
        }
        if (entity.getActualEnd() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getActualEnd());
        }
        statement.bindLong(9, entity.getBreakMinutes());
        if (entity.getScheduledWage() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getScheduledWage());
        }
        if (entity.getActualWage() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getActualWage());
        }
        if (entity.getConfirmedWage() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getConfirmedWage());
        }
        if (entity.getGoogleCalendarEventId() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getGoogleCalendarEventId());
        }
        if (entity.getNote() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getNote());
        }
        statement.bindLong(15, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfShiftEntity = new EntityDeletionOrUpdateAdapter<ShiftEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `shifts` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ShiftEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfShiftEntity = new EntityDeletionOrUpdateAdapter<ShiftEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `shifts` SET `id` = ?,`workplaceId` = ?,`workplaceName` = ?,`status` = ?,`scheduledStart` = ?,`scheduledEnd` = ?,`actualStart` = ?,`actualEnd` = ?,`breakMinutes` = ?,`scheduledWage` = ?,`actualWage` = ?,`confirmedWage` = ?,`googleCalendarEventId` = ?,`note` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ShiftEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getWorkplaceId());
        statement.bindString(3, entity.getWorkplaceName());
        final String _tmp = __converters.shiftStatusToString(entity.getStatus());
        statement.bindString(4, _tmp);
        statement.bindLong(5, entity.getScheduledStart());
        statement.bindLong(6, entity.getScheduledEnd());
        if (entity.getActualStart() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getActualStart());
        }
        if (entity.getActualEnd() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getActualEnd());
        }
        statement.bindLong(9, entity.getBreakMinutes());
        if (entity.getScheduledWage() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getScheduledWage());
        }
        if (entity.getActualWage() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getActualWage());
        }
        if (entity.getConfirmedWage() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getConfirmedWage());
        }
        if (entity.getGoogleCalendarEventId() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getGoogleCalendarEventId());
        }
        if (entity.getNote() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getNote());
        }
        statement.bindLong(15, entity.getCreatedAt());
        statement.bindLong(16, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateActual = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE shifts SET\n"
                + "            status = 'ACTUAL',\n"
                + "            actualStart = ?,\n"
                + "            actualEnd = ?,\n"
                + "            breakMinutes = ?,\n"
                + "            actualWage = ?\n"
                + "        WHERE id = ?\n"
                + "    ";
        return _query;
      }
    };
    this.__preparedStmtOfConfirm = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE shifts SET\n"
                + "            status = 'CONFIRMED',\n"
                + "            confirmedWage = ?\n"
                + "        WHERE id = ?\n"
                + "    ";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateCalendarEventId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE shifts SET googleCalendarEventId = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteByDateRange = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM shifts WHERE scheduledStart BETWEEN ? AND ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final ShiftEntity shift, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfShiftEntity.insertAndReturnId(shift);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final ShiftEntity shift, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfShiftEntity.handle(shift);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final ShiftEntity shift, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfShiftEntity.handle(shift);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateActual(final long id, final long actualStart, final long actualEnd,
      final int breakMinutes, final long actualWage, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateActual.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, actualStart);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, actualEnd);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, breakMinutes);
        _argIndex = 4;
        _stmt.bindLong(_argIndex, actualWage);
        _argIndex = 5;
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
          __preparedStmtOfUpdateActual.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object confirm(final long id, final long confirmedWage,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfConfirm.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, confirmedWage);
        _argIndex = 2;
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
          __preparedStmtOfConfirm.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCalendarEventId(final long id, final String eventId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateCalendarEventId.acquire();
        int _argIndex = 1;
        if (eventId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, eventId);
        }
        _argIndex = 2;
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
          __preparedStmtOfUpdateCalendarEventId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteByDateRange(final long fromMs, final long toMs,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByDateRange.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, fromMs);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, toMs);
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
  public Flow<List<ShiftEntity>> observeByDateRange(final long fromMs, final long toMs) {
    final String _sql = "SELECT * FROM shifts WHERE scheduledStart BETWEEN ? AND ? ORDER BY scheduledStart";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, fromMs);
    _argIndex = 2;
    _statement.bindLong(_argIndex, toMs);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"shifts"}, new Callable<List<ShiftEntity>>() {
      @Override
      @NonNull
      public List<ShiftEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWorkplaceId = CursorUtil.getColumnIndexOrThrow(_cursor, "workplaceId");
          final int _cursorIndexOfWorkplaceName = CursorUtil.getColumnIndexOrThrow(_cursor, "workplaceName");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfScheduledStart = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledStart");
          final int _cursorIndexOfScheduledEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledEnd");
          final int _cursorIndexOfActualStart = CursorUtil.getColumnIndexOrThrow(_cursor, "actualStart");
          final int _cursorIndexOfActualEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "actualEnd");
          final int _cursorIndexOfBreakMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "breakMinutes");
          final int _cursorIndexOfScheduledWage = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledWage");
          final int _cursorIndexOfActualWage = CursorUtil.getColumnIndexOrThrow(_cursor, "actualWage");
          final int _cursorIndexOfConfirmedWage = CursorUtil.getColumnIndexOrThrow(_cursor, "confirmedWage");
          final int _cursorIndexOfGoogleCalendarEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "googleCalendarEventId");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<ShiftEntity> _result = new ArrayList<ShiftEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ShiftEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWorkplaceId;
            _tmpWorkplaceId = _cursor.getLong(_cursorIndexOfWorkplaceId);
            final String _tmpWorkplaceName;
            _tmpWorkplaceName = _cursor.getString(_cursorIndexOfWorkplaceName);
            final ShiftStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.stringToShiftStatus(_tmp);
            final long _tmpScheduledStart;
            _tmpScheduledStart = _cursor.getLong(_cursorIndexOfScheduledStart);
            final long _tmpScheduledEnd;
            _tmpScheduledEnd = _cursor.getLong(_cursorIndexOfScheduledEnd);
            final Long _tmpActualStart;
            if (_cursor.isNull(_cursorIndexOfActualStart)) {
              _tmpActualStart = null;
            } else {
              _tmpActualStart = _cursor.getLong(_cursorIndexOfActualStart);
            }
            final Long _tmpActualEnd;
            if (_cursor.isNull(_cursorIndexOfActualEnd)) {
              _tmpActualEnd = null;
            } else {
              _tmpActualEnd = _cursor.getLong(_cursorIndexOfActualEnd);
            }
            final int _tmpBreakMinutes;
            _tmpBreakMinutes = _cursor.getInt(_cursorIndexOfBreakMinutes);
            final Long _tmpScheduledWage;
            if (_cursor.isNull(_cursorIndexOfScheduledWage)) {
              _tmpScheduledWage = null;
            } else {
              _tmpScheduledWage = _cursor.getLong(_cursorIndexOfScheduledWage);
            }
            final Long _tmpActualWage;
            if (_cursor.isNull(_cursorIndexOfActualWage)) {
              _tmpActualWage = null;
            } else {
              _tmpActualWage = _cursor.getLong(_cursorIndexOfActualWage);
            }
            final Long _tmpConfirmedWage;
            if (_cursor.isNull(_cursorIndexOfConfirmedWage)) {
              _tmpConfirmedWage = null;
            } else {
              _tmpConfirmedWage = _cursor.getLong(_cursorIndexOfConfirmedWage);
            }
            final String _tmpGoogleCalendarEventId;
            if (_cursor.isNull(_cursorIndexOfGoogleCalendarEventId)) {
              _tmpGoogleCalendarEventId = null;
            } else {
              _tmpGoogleCalendarEventId = _cursor.getString(_cursorIndexOfGoogleCalendarEventId);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new ShiftEntity(_tmpId,_tmpWorkplaceId,_tmpWorkplaceName,_tmpStatus,_tmpScheduledStart,_tmpScheduledEnd,_tmpActualStart,_tmpActualEnd,_tmpBreakMinutes,_tmpScheduledWage,_tmpActualWage,_tmpConfirmedWage,_tmpGoogleCalendarEventId,_tmpNote,_tmpCreatedAt);
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
  public Object findById(final long id, final Continuation<? super ShiftEntity> $completion) {
    final String _sql = "SELECT * FROM shifts WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ShiftEntity>() {
      @Override
      @Nullable
      public ShiftEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWorkplaceId = CursorUtil.getColumnIndexOrThrow(_cursor, "workplaceId");
          final int _cursorIndexOfWorkplaceName = CursorUtil.getColumnIndexOrThrow(_cursor, "workplaceName");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfScheduledStart = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledStart");
          final int _cursorIndexOfScheduledEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledEnd");
          final int _cursorIndexOfActualStart = CursorUtil.getColumnIndexOrThrow(_cursor, "actualStart");
          final int _cursorIndexOfActualEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "actualEnd");
          final int _cursorIndexOfBreakMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "breakMinutes");
          final int _cursorIndexOfScheduledWage = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledWage");
          final int _cursorIndexOfActualWage = CursorUtil.getColumnIndexOrThrow(_cursor, "actualWage");
          final int _cursorIndexOfConfirmedWage = CursorUtil.getColumnIndexOrThrow(_cursor, "confirmedWage");
          final int _cursorIndexOfGoogleCalendarEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "googleCalendarEventId");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final ShiftEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWorkplaceId;
            _tmpWorkplaceId = _cursor.getLong(_cursorIndexOfWorkplaceId);
            final String _tmpWorkplaceName;
            _tmpWorkplaceName = _cursor.getString(_cursorIndexOfWorkplaceName);
            final ShiftStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.stringToShiftStatus(_tmp);
            final long _tmpScheduledStart;
            _tmpScheduledStart = _cursor.getLong(_cursorIndexOfScheduledStart);
            final long _tmpScheduledEnd;
            _tmpScheduledEnd = _cursor.getLong(_cursorIndexOfScheduledEnd);
            final Long _tmpActualStart;
            if (_cursor.isNull(_cursorIndexOfActualStart)) {
              _tmpActualStart = null;
            } else {
              _tmpActualStart = _cursor.getLong(_cursorIndexOfActualStart);
            }
            final Long _tmpActualEnd;
            if (_cursor.isNull(_cursorIndexOfActualEnd)) {
              _tmpActualEnd = null;
            } else {
              _tmpActualEnd = _cursor.getLong(_cursorIndexOfActualEnd);
            }
            final int _tmpBreakMinutes;
            _tmpBreakMinutes = _cursor.getInt(_cursorIndexOfBreakMinutes);
            final Long _tmpScheduledWage;
            if (_cursor.isNull(_cursorIndexOfScheduledWage)) {
              _tmpScheduledWage = null;
            } else {
              _tmpScheduledWage = _cursor.getLong(_cursorIndexOfScheduledWage);
            }
            final Long _tmpActualWage;
            if (_cursor.isNull(_cursorIndexOfActualWage)) {
              _tmpActualWage = null;
            } else {
              _tmpActualWage = _cursor.getLong(_cursorIndexOfActualWage);
            }
            final Long _tmpConfirmedWage;
            if (_cursor.isNull(_cursorIndexOfConfirmedWage)) {
              _tmpConfirmedWage = null;
            } else {
              _tmpConfirmedWage = _cursor.getLong(_cursorIndexOfConfirmedWage);
            }
            final String _tmpGoogleCalendarEventId;
            if (_cursor.isNull(_cursorIndexOfGoogleCalendarEventId)) {
              _tmpGoogleCalendarEventId = null;
            } else {
              _tmpGoogleCalendarEventId = _cursor.getString(_cursorIndexOfGoogleCalendarEventId);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new ShiftEntity(_tmpId,_tmpWorkplaceId,_tmpWorkplaceName,_tmpStatus,_tmpScheduledStart,_tmpScheduledEnd,_tmpActualStart,_tmpActualEnd,_tmpBreakMinutes,_tmpScheduledWage,_tmpActualWage,_tmpConfirmedWage,_tmpGoogleCalendarEventId,_tmpNote,_tmpCreatedAt);
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
  public Flow<List<ShiftEntity>> observeByStatus(final ShiftStatus status) {
    final String _sql = "SELECT * FROM shifts WHERE status = ? ORDER BY scheduledStart";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.shiftStatusToString(status);
    _statement.bindString(_argIndex, _tmp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"shifts"}, new Callable<List<ShiftEntity>>() {
      @Override
      @NonNull
      public List<ShiftEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWorkplaceId = CursorUtil.getColumnIndexOrThrow(_cursor, "workplaceId");
          final int _cursorIndexOfWorkplaceName = CursorUtil.getColumnIndexOrThrow(_cursor, "workplaceName");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfScheduledStart = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledStart");
          final int _cursorIndexOfScheduledEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledEnd");
          final int _cursorIndexOfActualStart = CursorUtil.getColumnIndexOrThrow(_cursor, "actualStart");
          final int _cursorIndexOfActualEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "actualEnd");
          final int _cursorIndexOfBreakMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "breakMinutes");
          final int _cursorIndexOfScheduledWage = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledWage");
          final int _cursorIndexOfActualWage = CursorUtil.getColumnIndexOrThrow(_cursor, "actualWage");
          final int _cursorIndexOfConfirmedWage = CursorUtil.getColumnIndexOrThrow(_cursor, "confirmedWage");
          final int _cursorIndexOfGoogleCalendarEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "googleCalendarEventId");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<ShiftEntity> _result = new ArrayList<ShiftEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ShiftEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWorkplaceId;
            _tmpWorkplaceId = _cursor.getLong(_cursorIndexOfWorkplaceId);
            final String _tmpWorkplaceName;
            _tmpWorkplaceName = _cursor.getString(_cursorIndexOfWorkplaceName);
            final ShiftStatus _tmpStatus;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.stringToShiftStatus(_tmp_1);
            final long _tmpScheduledStart;
            _tmpScheduledStart = _cursor.getLong(_cursorIndexOfScheduledStart);
            final long _tmpScheduledEnd;
            _tmpScheduledEnd = _cursor.getLong(_cursorIndexOfScheduledEnd);
            final Long _tmpActualStart;
            if (_cursor.isNull(_cursorIndexOfActualStart)) {
              _tmpActualStart = null;
            } else {
              _tmpActualStart = _cursor.getLong(_cursorIndexOfActualStart);
            }
            final Long _tmpActualEnd;
            if (_cursor.isNull(_cursorIndexOfActualEnd)) {
              _tmpActualEnd = null;
            } else {
              _tmpActualEnd = _cursor.getLong(_cursorIndexOfActualEnd);
            }
            final int _tmpBreakMinutes;
            _tmpBreakMinutes = _cursor.getInt(_cursorIndexOfBreakMinutes);
            final Long _tmpScheduledWage;
            if (_cursor.isNull(_cursorIndexOfScheduledWage)) {
              _tmpScheduledWage = null;
            } else {
              _tmpScheduledWage = _cursor.getLong(_cursorIndexOfScheduledWage);
            }
            final Long _tmpActualWage;
            if (_cursor.isNull(_cursorIndexOfActualWage)) {
              _tmpActualWage = null;
            } else {
              _tmpActualWage = _cursor.getLong(_cursorIndexOfActualWage);
            }
            final Long _tmpConfirmedWage;
            if (_cursor.isNull(_cursorIndexOfConfirmedWage)) {
              _tmpConfirmedWage = null;
            } else {
              _tmpConfirmedWage = _cursor.getLong(_cursorIndexOfConfirmedWage);
            }
            final String _tmpGoogleCalendarEventId;
            if (_cursor.isNull(_cursorIndexOfGoogleCalendarEventId)) {
              _tmpGoogleCalendarEventId = null;
            } else {
              _tmpGoogleCalendarEventId = _cursor.getString(_cursorIndexOfGoogleCalendarEventId);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new ShiftEntity(_tmpId,_tmpWorkplaceId,_tmpWorkplaceName,_tmpStatus,_tmpScheduledStart,_tmpScheduledEnd,_tmpActualStart,_tmpActualEnd,_tmpBreakMinutes,_tmpScheduledWage,_tmpActualWage,_tmpConfirmedWage,_tmpGoogleCalendarEventId,_tmpNote,_tmpCreatedAt);
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
  public Object getUnconfirmed(final Continuation<? super List<ShiftEntity>> $completion) {
    final String _sql = "SELECT * FROM shifts WHERE status IN ('SCHEDULED', 'ACTUAL') ORDER BY scheduledStart";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ShiftEntity>>() {
      @Override
      @NonNull
      public List<ShiftEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWorkplaceId = CursorUtil.getColumnIndexOrThrow(_cursor, "workplaceId");
          final int _cursorIndexOfWorkplaceName = CursorUtil.getColumnIndexOrThrow(_cursor, "workplaceName");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfScheduledStart = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledStart");
          final int _cursorIndexOfScheduledEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledEnd");
          final int _cursorIndexOfActualStart = CursorUtil.getColumnIndexOrThrow(_cursor, "actualStart");
          final int _cursorIndexOfActualEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "actualEnd");
          final int _cursorIndexOfBreakMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "breakMinutes");
          final int _cursorIndexOfScheduledWage = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledWage");
          final int _cursorIndexOfActualWage = CursorUtil.getColumnIndexOrThrow(_cursor, "actualWage");
          final int _cursorIndexOfConfirmedWage = CursorUtil.getColumnIndexOrThrow(_cursor, "confirmedWage");
          final int _cursorIndexOfGoogleCalendarEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "googleCalendarEventId");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<ShiftEntity> _result = new ArrayList<ShiftEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ShiftEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWorkplaceId;
            _tmpWorkplaceId = _cursor.getLong(_cursorIndexOfWorkplaceId);
            final String _tmpWorkplaceName;
            _tmpWorkplaceName = _cursor.getString(_cursorIndexOfWorkplaceName);
            final ShiftStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.stringToShiftStatus(_tmp);
            final long _tmpScheduledStart;
            _tmpScheduledStart = _cursor.getLong(_cursorIndexOfScheduledStart);
            final long _tmpScheduledEnd;
            _tmpScheduledEnd = _cursor.getLong(_cursorIndexOfScheduledEnd);
            final Long _tmpActualStart;
            if (_cursor.isNull(_cursorIndexOfActualStart)) {
              _tmpActualStart = null;
            } else {
              _tmpActualStart = _cursor.getLong(_cursorIndexOfActualStart);
            }
            final Long _tmpActualEnd;
            if (_cursor.isNull(_cursorIndexOfActualEnd)) {
              _tmpActualEnd = null;
            } else {
              _tmpActualEnd = _cursor.getLong(_cursorIndexOfActualEnd);
            }
            final int _tmpBreakMinutes;
            _tmpBreakMinutes = _cursor.getInt(_cursorIndexOfBreakMinutes);
            final Long _tmpScheduledWage;
            if (_cursor.isNull(_cursorIndexOfScheduledWage)) {
              _tmpScheduledWage = null;
            } else {
              _tmpScheduledWage = _cursor.getLong(_cursorIndexOfScheduledWage);
            }
            final Long _tmpActualWage;
            if (_cursor.isNull(_cursorIndexOfActualWage)) {
              _tmpActualWage = null;
            } else {
              _tmpActualWage = _cursor.getLong(_cursorIndexOfActualWage);
            }
            final Long _tmpConfirmedWage;
            if (_cursor.isNull(_cursorIndexOfConfirmedWage)) {
              _tmpConfirmedWage = null;
            } else {
              _tmpConfirmedWage = _cursor.getLong(_cursorIndexOfConfirmedWage);
            }
            final String _tmpGoogleCalendarEventId;
            if (_cursor.isNull(_cursorIndexOfGoogleCalendarEventId)) {
              _tmpGoogleCalendarEventId = null;
            } else {
              _tmpGoogleCalendarEventId = _cursor.getString(_cursorIndexOfGoogleCalendarEventId);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new ShiftEntity(_tmpId,_tmpWorkplaceId,_tmpWorkplaceName,_tmpStatus,_tmpScheduledStart,_tmpScheduledEnd,_tmpActualStart,_tmpActualEnd,_tmpBreakMinutes,_tmpScheduledWage,_tmpActualWage,_tmpConfirmedWage,_tmpGoogleCalendarEventId,_tmpNote,_tmpCreatedAt);
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
