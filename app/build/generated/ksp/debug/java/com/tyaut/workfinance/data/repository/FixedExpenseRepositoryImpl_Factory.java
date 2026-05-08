package com.tyaut.workfinance.data.repository;

import com.tyaut.workfinance.data.db.dao.FixedExpenseDao;
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
public final class FixedExpenseRepositoryImpl_Factory implements Factory<FixedExpenseRepositoryImpl> {
  private final Provider<FixedExpenseDao> daoProvider;

  public FixedExpenseRepositoryImpl_Factory(Provider<FixedExpenseDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public FixedExpenseRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static FixedExpenseRepositoryImpl_Factory create(Provider<FixedExpenseDao> daoProvider) {
    return new FixedExpenseRepositoryImpl_Factory(daoProvider);
  }

  public static FixedExpenseRepositoryImpl newInstance(FixedExpenseDao dao) {
    return new FixedExpenseRepositoryImpl(dao);
  }
}
