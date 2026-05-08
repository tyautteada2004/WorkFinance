package com.tyaut.workfinance.data.repository;

import com.tyaut.workfinance.data.db.dao.AccountDao;
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
public final class AccountRepositoryImpl_Factory implements Factory<AccountRepositoryImpl> {
  private final Provider<AccountDao> daoProvider;

  public AccountRepositoryImpl_Factory(Provider<AccountDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public AccountRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static AccountRepositoryImpl_Factory create(Provider<AccountDao> daoProvider) {
    return new AccountRepositoryImpl_Factory(daoProvider);
  }

  public static AccountRepositoryImpl newInstance(AccountDao dao) {
    return new AccountRepositoryImpl(dao);
  }
}
