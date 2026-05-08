package com.tyaut.workfinance.data.repository;

import com.tyaut.workfinance.data.db.dao.MonthlySummaryDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class MonthlySummaryRepositoryImpl_Factory implements Factory<MonthlySummaryRepositoryImpl> {
  private final Provider<MonthlySummaryDao> daoProvider;

  public MonthlySummaryRepositoryImpl_Factory(Provider<MonthlySummaryDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public MonthlySummaryRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static MonthlySummaryRepositoryImpl_Factory create(
      Provider<MonthlySummaryDao> daoProvider) {
    return new MonthlySummaryRepositoryImpl_Factory(daoProvider);
  }

  public static MonthlySummaryRepositoryImpl newInstance(MonthlySummaryDao dao) {
    return new MonthlySummaryRepositoryImpl(dao);
  }
}
