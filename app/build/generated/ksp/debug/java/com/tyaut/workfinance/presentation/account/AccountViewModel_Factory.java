package com.tyaut.workfinance.presentation.account;

import com.tyaut.workfinance.data.repository.AccountRepository;
import com.tyaut.workfinance.data.repository.TransactionRepository;
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
public final class AccountViewModel_Factory implements Factory<AccountViewModel> {
  private final Provider<AccountRepository> accountRepoProvider;

  private final Provider<TransactionRepository> transactionRepoProvider;

  public AccountViewModel_Factory(Provider<AccountRepository> accountRepoProvider,
      Provider<TransactionRepository> transactionRepoProvider) {
    this.accountRepoProvider = accountRepoProvider;
    this.transactionRepoProvider = transactionRepoProvider;
  }

  @Override
  public AccountViewModel get() {
    return newInstance(accountRepoProvider.get(), transactionRepoProvider.get());
  }

  public static AccountViewModel_Factory create(Provider<AccountRepository> accountRepoProvider,
      Provider<TransactionRepository> transactionRepoProvider) {
    return new AccountViewModel_Factory(accountRepoProvider, transactionRepoProvider);
  }

  public static AccountViewModel newInstance(AccountRepository accountRepo,
      TransactionRepository transactionRepo) {
    return new AccountViewModel(accountRepo, transactionRepo);
  }
}
