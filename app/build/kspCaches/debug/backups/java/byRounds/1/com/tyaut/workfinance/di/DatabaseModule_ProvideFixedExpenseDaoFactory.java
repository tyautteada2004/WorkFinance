package com.tyaut.workfinance.di;

import com.tyaut.workfinance.data.db.AppDatabase;
import com.tyaut.workfinance.data.db.dao.FixedExpenseDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class DatabaseModule_ProvideFixedExpenseDaoFactory implements Factory<FixedExpenseDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideFixedExpenseDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public FixedExpenseDao get() {
    return provideFixedExpenseDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideFixedExpenseDaoFactory create(
      Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideFixedExpenseDaoFactory(dbProvider);
  }

  public static FixedExpenseDao provideFixedExpenseDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideFixedExpenseDao(db));
  }
}
