package com.tyaut.workfinance.di;

import com.tyaut.workfinance.data.db.AppDatabase;
import com.tyaut.workfinance.data.db.dao.MonthlySummaryDao;
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
public final class DatabaseModule_ProvideMonthlySummaryDaoFactory implements Factory<MonthlySummaryDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideMonthlySummaryDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public MonthlySummaryDao get() {
    return provideMonthlySummaryDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideMonthlySummaryDaoFactory create(
      Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideMonthlySummaryDaoFactory(dbProvider);
  }

  public static MonthlySummaryDao provideMonthlySummaryDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideMonthlySummaryDao(db));
  }
}
