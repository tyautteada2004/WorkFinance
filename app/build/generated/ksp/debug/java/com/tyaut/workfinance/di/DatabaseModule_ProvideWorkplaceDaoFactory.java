package com.tyaut.workfinance.di;

import com.tyaut.workfinance.data.db.AppDatabase;
import com.tyaut.workfinance.data.db.dao.WorkplaceDao;
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
public final class DatabaseModule_ProvideWorkplaceDaoFactory implements Factory<WorkplaceDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideWorkplaceDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public WorkplaceDao get() {
    return provideWorkplaceDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideWorkplaceDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideWorkplaceDaoFactory(dbProvider);
  }

  public static WorkplaceDao provideWorkplaceDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideWorkplaceDao(db));
  }
}
