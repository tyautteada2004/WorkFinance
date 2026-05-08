package com.tyaut.workfinance.data.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.tyaut.workfinance.data.db.dao.AccountDao;
import com.tyaut.workfinance.data.db.dao.AccountDao_Impl;
import com.tyaut.workfinance.data.db.dao.FixedExpenseDao;
import com.tyaut.workfinance.data.db.dao.FixedExpenseDao_Impl;
import com.tyaut.workfinance.data.db.dao.MonthlySummaryDao;
import com.tyaut.workfinance.data.db.dao.MonthlySummaryDao_Impl;
import com.tyaut.workfinance.data.db.dao.ShiftDao;
import com.tyaut.workfinance.data.db.dao.ShiftDao_Impl;
import com.tyaut.workfinance.data.db.dao.TransactionDao;
import com.tyaut.workfinance.data.db.dao.TransactionDao_Impl;
import com.tyaut.workfinance.data.db.dao.WorkplaceDao;
import com.tyaut.workfinance.data.db.dao.WorkplaceDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile AccountDao _accountDao;

  private volatile TransactionDao _transactionDao;

  private volatile ShiftDao _shiftDao;

  private volatile WorkplaceDao _workplaceDao;

  private volatile FixedExpenseDao _fixedExpenseDao;

  private volatile MonthlySummaryDao _monthlySummaryDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `accounts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `type` TEXT NOT NULL, `initialBalance` INTEGER NOT NULL, `closingDay` INTEGER, `paymentDay` INTEGER, `businessDayRule` TEXT, `isActive` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `transactions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `accountId` INTEGER NOT NULL, `amount` INTEGER NOT NULL, `type` TEXT NOT NULL, `category` TEXT, `description` TEXT NOT NULL, `transactionDate` INTEGER NOT NULL, `isConfirmed` INTEGER NOT NULL, `creditCardPaymentMonth` TEXT, `pairedTransactionId` INTEGER, `isFromFixedExpense` INTEGER NOT NULL, `fixedExpenseId` INTEGER, `note` TEXT, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, FOREIGN KEY(`accountId`) REFERENCES `accounts`(`id`) ON UPDATE NO ACTION ON DELETE RESTRICT )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_transactions_accountId` ON `transactions` (`accountId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_transactions_transactionDate` ON `transactions` (`transactionDate`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_transactions_creditCardPaymentMonth` ON `transactions` (`creditCardPaymentMonth`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `shifts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `workplaceId` INTEGER NOT NULL, `workplaceName` TEXT NOT NULL, `status` TEXT NOT NULL, `scheduledStart` INTEGER NOT NULL, `scheduledEnd` INTEGER NOT NULL, `actualStart` INTEGER, `actualEnd` INTEGER, `breakMinutes` INTEGER NOT NULL, `scheduledWage` INTEGER, `actualWage` INTEGER, `confirmedWage` INTEGER, `googleCalendarEventId` TEXT, `note` TEXT, `createdAt` INTEGER NOT NULL, FOREIGN KEY(`workplaceId`) REFERENCES `workplaces`(`id`) ON UPDATE NO ACTION ON DELETE RESTRICT )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_shifts_workplaceId` ON `shifts` (`workplaceId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_shifts_scheduledStart` ON `shifts` (`scheduledStart`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `workplaces` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `wageType` TEXT NOT NULL, `hourlyWage` INTEGER, `monthlyWage` INTEGER, `nightPremiumRate` REAL NOT NULL, `roundingRule` TEXT NOT NULL, `commissionEnabled` INTEGER NOT NULL, `payDay` INTEGER NOT NULL, `payDayBusinessDayRule` TEXT, `depositAccountId` INTEGER NOT NULL, `preWorkNotifyMinutes` INTEGER NOT NULL, `isActive` INTEGER NOT NULL, FOREIGN KEY(`depositAccountId`) REFERENCES `accounts`(`id`) ON UPDATE NO ACTION ON DELETE RESTRICT )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_workplaces_depositAccountId` ON `workplaces` (`depositAccountId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `fixed_expenses` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `amount` INTEGER NOT NULL, `category` TEXT NOT NULL, `accountId` INTEGER NOT NULL, `accountName` TEXT NOT NULL, `startDate` INTEGER NOT NULL, `endDate` INTEGER, `dayOfMonth` INTEGER NOT NULL, `businessDayRule` TEXT, `isActive` INTEGER NOT NULL, FOREIGN KEY(`accountId`) REFERENCES `accounts`(`id`) ON UPDATE NO ACTION ON DELETE RESTRICT )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_fixed_expenses_accountId` ON `fixed_expenses` (`accountId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `monthly_summaries` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `yearMonth` TEXT NOT NULL, `totalIncome` INTEGER NOT NULL, `totalExpense` INTEGER NOT NULL, `totalWorkMinutes` INTEGER NOT NULL, `totalConsumption` INTEGER NOT NULL, `totalInvestment` INTEGER NOT NULL, `totalWaste` INTEGER NOT NULL, `isArchived` INTEGER NOT NULL, `archiveFilePath` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'ec02d2b6c7f23b71306358fe1672bfc1')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `accounts`");
        db.execSQL("DROP TABLE IF EXISTS `transactions`");
        db.execSQL("DROP TABLE IF EXISTS `shifts`");
        db.execSQL("DROP TABLE IF EXISTS `workplaces`");
        db.execSQL("DROP TABLE IF EXISTS `fixed_expenses`");
        db.execSQL("DROP TABLE IF EXISTS `monthly_summaries`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsAccounts = new HashMap<String, TableInfo.Column>(9);
        _columnsAccounts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccounts.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccounts.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccounts.put("initialBalance", new TableInfo.Column("initialBalance", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccounts.put("closingDay", new TableInfo.Column("closingDay", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccounts.put("paymentDay", new TableInfo.Column("paymentDay", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccounts.put("businessDayRule", new TableInfo.Column("businessDayRule", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccounts.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccounts.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAccounts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAccounts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAccounts = new TableInfo("accounts", _columnsAccounts, _foreignKeysAccounts, _indicesAccounts);
        final TableInfo _existingAccounts = TableInfo.read(db, "accounts");
        if (!_infoAccounts.equals(_existingAccounts)) {
          return new RoomOpenHelper.ValidationResult(false, "accounts(com.tyaut.workfinance.data.db.entity.AccountEntity).\n"
                  + " Expected:\n" + _infoAccounts + "\n"
                  + " Found:\n" + _existingAccounts);
        }
        final HashMap<String, TableInfo.Column> _columnsTransactions = new HashMap<String, TableInfo.Column>(15);
        _columnsTransactions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("accountId", new TableInfo.Column("accountId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("amount", new TableInfo.Column("amount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("category", new TableInfo.Column("category", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("transactionDate", new TableInfo.Column("transactionDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("isConfirmed", new TableInfo.Column("isConfirmed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("creditCardPaymentMonth", new TableInfo.Column("creditCardPaymentMonth", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("pairedTransactionId", new TableInfo.Column("pairedTransactionId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("isFromFixedExpense", new TableInfo.Column("isFromFixedExpense", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("fixedExpenseId", new TableInfo.Column("fixedExpenseId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("note", new TableInfo.Column("note", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTransactions = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysTransactions.add(new TableInfo.ForeignKey("accounts", "RESTRICT", "NO ACTION", Arrays.asList("accountId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesTransactions = new HashSet<TableInfo.Index>(3);
        _indicesTransactions.add(new TableInfo.Index("index_transactions_accountId", false, Arrays.asList("accountId"), Arrays.asList("ASC")));
        _indicesTransactions.add(new TableInfo.Index("index_transactions_transactionDate", false, Arrays.asList("transactionDate"), Arrays.asList("ASC")));
        _indicesTransactions.add(new TableInfo.Index("index_transactions_creditCardPaymentMonth", false, Arrays.asList("creditCardPaymentMonth"), Arrays.asList("ASC")));
        final TableInfo _infoTransactions = new TableInfo("transactions", _columnsTransactions, _foreignKeysTransactions, _indicesTransactions);
        final TableInfo _existingTransactions = TableInfo.read(db, "transactions");
        if (!_infoTransactions.equals(_existingTransactions)) {
          return new RoomOpenHelper.ValidationResult(false, "transactions(com.tyaut.workfinance.data.db.entity.TransactionEntity).\n"
                  + " Expected:\n" + _infoTransactions + "\n"
                  + " Found:\n" + _existingTransactions);
        }
        final HashMap<String, TableInfo.Column> _columnsShifts = new HashMap<String, TableInfo.Column>(15);
        _columnsShifts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("workplaceId", new TableInfo.Column("workplaceId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("workplaceName", new TableInfo.Column("workplaceName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("scheduledStart", new TableInfo.Column("scheduledStart", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("scheduledEnd", new TableInfo.Column("scheduledEnd", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("actualStart", new TableInfo.Column("actualStart", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("actualEnd", new TableInfo.Column("actualEnd", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("breakMinutes", new TableInfo.Column("breakMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("scheduledWage", new TableInfo.Column("scheduledWage", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("actualWage", new TableInfo.Column("actualWage", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("confirmedWage", new TableInfo.Column("confirmedWage", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("googleCalendarEventId", new TableInfo.Column("googleCalendarEventId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("note", new TableInfo.Column("note", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysShifts = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysShifts.add(new TableInfo.ForeignKey("workplaces", "RESTRICT", "NO ACTION", Arrays.asList("workplaceId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesShifts = new HashSet<TableInfo.Index>(2);
        _indicesShifts.add(new TableInfo.Index("index_shifts_workplaceId", false, Arrays.asList("workplaceId"), Arrays.asList("ASC")));
        _indicesShifts.add(new TableInfo.Index("index_shifts_scheduledStart", false, Arrays.asList("scheduledStart"), Arrays.asList("ASC")));
        final TableInfo _infoShifts = new TableInfo("shifts", _columnsShifts, _foreignKeysShifts, _indicesShifts);
        final TableInfo _existingShifts = TableInfo.read(db, "shifts");
        if (!_infoShifts.equals(_existingShifts)) {
          return new RoomOpenHelper.ValidationResult(false, "shifts(com.tyaut.workfinance.data.db.entity.ShiftEntity).\n"
                  + " Expected:\n" + _infoShifts + "\n"
                  + " Found:\n" + _existingShifts);
        }
        final HashMap<String, TableInfo.Column> _columnsWorkplaces = new HashMap<String, TableInfo.Column>(13);
        _columnsWorkplaces.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkplaces.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkplaces.put("wageType", new TableInfo.Column("wageType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkplaces.put("hourlyWage", new TableInfo.Column("hourlyWage", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkplaces.put("monthlyWage", new TableInfo.Column("monthlyWage", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkplaces.put("nightPremiumRate", new TableInfo.Column("nightPremiumRate", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkplaces.put("roundingRule", new TableInfo.Column("roundingRule", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkplaces.put("commissionEnabled", new TableInfo.Column("commissionEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkplaces.put("payDay", new TableInfo.Column("payDay", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkplaces.put("payDayBusinessDayRule", new TableInfo.Column("payDayBusinessDayRule", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkplaces.put("depositAccountId", new TableInfo.Column("depositAccountId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkplaces.put("preWorkNotifyMinutes", new TableInfo.Column("preWorkNotifyMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkplaces.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWorkplaces = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysWorkplaces.add(new TableInfo.ForeignKey("accounts", "RESTRICT", "NO ACTION", Arrays.asList("depositAccountId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesWorkplaces = new HashSet<TableInfo.Index>(1);
        _indicesWorkplaces.add(new TableInfo.Index("index_workplaces_depositAccountId", false, Arrays.asList("depositAccountId"), Arrays.asList("ASC")));
        final TableInfo _infoWorkplaces = new TableInfo("workplaces", _columnsWorkplaces, _foreignKeysWorkplaces, _indicesWorkplaces);
        final TableInfo _existingWorkplaces = TableInfo.read(db, "workplaces");
        if (!_infoWorkplaces.equals(_existingWorkplaces)) {
          return new RoomOpenHelper.ValidationResult(false, "workplaces(com.tyaut.workfinance.data.db.entity.WorkplaceEntity).\n"
                  + " Expected:\n" + _infoWorkplaces + "\n"
                  + " Found:\n" + _existingWorkplaces);
        }
        final HashMap<String, TableInfo.Column> _columnsFixedExpenses = new HashMap<String, TableInfo.Column>(11);
        _columnsFixedExpenses.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFixedExpenses.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFixedExpenses.put("amount", new TableInfo.Column("amount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFixedExpenses.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFixedExpenses.put("accountId", new TableInfo.Column("accountId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFixedExpenses.put("accountName", new TableInfo.Column("accountName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFixedExpenses.put("startDate", new TableInfo.Column("startDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFixedExpenses.put("endDate", new TableInfo.Column("endDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFixedExpenses.put("dayOfMonth", new TableInfo.Column("dayOfMonth", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFixedExpenses.put("businessDayRule", new TableInfo.Column("businessDayRule", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFixedExpenses.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFixedExpenses = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysFixedExpenses.add(new TableInfo.ForeignKey("accounts", "RESTRICT", "NO ACTION", Arrays.asList("accountId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesFixedExpenses = new HashSet<TableInfo.Index>(1);
        _indicesFixedExpenses.add(new TableInfo.Index("index_fixed_expenses_accountId", false, Arrays.asList("accountId"), Arrays.asList("ASC")));
        final TableInfo _infoFixedExpenses = new TableInfo("fixed_expenses", _columnsFixedExpenses, _foreignKeysFixedExpenses, _indicesFixedExpenses);
        final TableInfo _existingFixedExpenses = TableInfo.read(db, "fixed_expenses");
        if (!_infoFixedExpenses.equals(_existingFixedExpenses)) {
          return new RoomOpenHelper.ValidationResult(false, "fixed_expenses(com.tyaut.workfinance.data.db.entity.FixedExpenseEntity).\n"
                  + " Expected:\n" + _infoFixedExpenses + "\n"
                  + " Found:\n" + _existingFixedExpenses);
        }
        final HashMap<String, TableInfo.Column> _columnsMonthlySummaries = new HashMap<String, TableInfo.Column>(10);
        _columnsMonthlySummaries.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMonthlySummaries.put("yearMonth", new TableInfo.Column("yearMonth", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMonthlySummaries.put("totalIncome", new TableInfo.Column("totalIncome", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMonthlySummaries.put("totalExpense", new TableInfo.Column("totalExpense", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMonthlySummaries.put("totalWorkMinutes", new TableInfo.Column("totalWorkMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMonthlySummaries.put("totalConsumption", new TableInfo.Column("totalConsumption", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMonthlySummaries.put("totalInvestment", new TableInfo.Column("totalInvestment", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMonthlySummaries.put("totalWaste", new TableInfo.Column("totalWaste", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMonthlySummaries.put("isArchived", new TableInfo.Column("isArchived", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMonthlySummaries.put("archiveFilePath", new TableInfo.Column("archiveFilePath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMonthlySummaries = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMonthlySummaries = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMonthlySummaries = new TableInfo("monthly_summaries", _columnsMonthlySummaries, _foreignKeysMonthlySummaries, _indicesMonthlySummaries);
        final TableInfo _existingMonthlySummaries = TableInfo.read(db, "monthly_summaries");
        if (!_infoMonthlySummaries.equals(_existingMonthlySummaries)) {
          return new RoomOpenHelper.ValidationResult(false, "monthly_summaries(com.tyaut.workfinance.data.db.entity.MonthlySummaryEntity).\n"
                  + " Expected:\n" + _infoMonthlySummaries + "\n"
                  + " Found:\n" + _existingMonthlySummaries);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "ec02d2b6c7f23b71306358fe1672bfc1", "11c7f5a017cfad1577c64e5614b40ea6");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "accounts","transactions","shifts","workplaces","fixed_expenses","monthly_summaries");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `transactions`");
      _db.execSQL("DELETE FROM `workplaces`");
      _db.execSQL("DELETE FROM `fixed_expenses`");
      _db.execSQL("DELETE FROM `accounts`");
      _db.execSQL("DELETE FROM `shifts`");
      _db.execSQL("DELETE FROM `monthly_summaries`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(AccountDao.class, AccountDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TransactionDao.class, TransactionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ShiftDao.class, ShiftDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(WorkplaceDao.class, WorkplaceDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FixedExpenseDao.class, FixedExpenseDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MonthlySummaryDao.class, MonthlySummaryDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public AccountDao accountDao() {
    if (_accountDao != null) {
      return _accountDao;
    } else {
      synchronized(this) {
        if(_accountDao == null) {
          _accountDao = new AccountDao_Impl(this);
        }
        return _accountDao;
      }
    }
  }

  @Override
  public TransactionDao transactionDao() {
    if (_transactionDao != null) {
      return _transactionDao;
    } else {
      synchronized(this) {
        if(_transactionDao == null) {
          _transactionDao = new TransactionDao_Impl(this);
        }
        return _transactionDao;
      }
    }
  }

  @Override
  public ShiftDao shiftDao() {
    if (_shiftDao != null) {
      return _shiftDao;
    } else {
      synchronized(this) {
        if(_shiftDao == null) {
          _shiftDao = new ShiftDao_Impl(this);
        }
        return _shiftDao;
      }
    }
  }

  @Override
  public WorkplaceDao workplaceDao() {
    if (_workplaceDao != null) {
      return _workplaceDao;
    } else {
      synchronized(this) {
        if(_workplaceDao == null) {
          _workplaceDao = new WorkplaceDao_Impl(this);
        }
        return _workplaceDao;
      }
    }
  }

  @Override
  public FixedExpenseDao fixedExpenseDao() {
    if (_fixedExpenseDao != null) {
      return _fixedExpenseDao;
    } else {
      synchronized(this) {
        if(_fixedExpenseDao == null) {
          _fixedExpenseDao = new FixedExpenseDao_Impl(this);
        }
        return _fixedExpenseDao;
      }
    }
  }

  @Override
  public MonthlySummaryDao monthlySummaryDao() {
    if (_monthlySummaryDao != null) {
      return _monthlySummaryDao;
    } else {
      synchronized(this) {
        if(_monthlySummaryDao == null) {
          _monthlySummaryDao = new MonthlySummaryDao_Impl(this);
        }
        return _monthlySummaryDao;
      }
    }
  }
}
