package com.tyaut.workfinance.di;

import com.tyaut.workfinance.data.db.AppDatabase;
import com.tyaut.workfinance.data.db.dao.ShiftDao;
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
public final class DatabaseModule_ProvideShiftDaoFactory implements Factory<ShiftDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideShiftDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ShiftDao get() {
    return provideShiftDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideShiftDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideShiftDaoFactory(dbProvider);
  }

  public static ShiftDao provideShiftDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideShiftDao(db));
  }
}
