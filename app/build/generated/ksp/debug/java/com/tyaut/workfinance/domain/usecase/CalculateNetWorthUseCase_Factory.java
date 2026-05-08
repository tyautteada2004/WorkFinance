package com.tyaut.workfinance.domain.usecase;

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
public final class CalculateNetWorthUseCase_Factory implements Factory<CalculateNetWorthUseCase> {
  private final Provider<AccountRepository> accountRepoProvider;

  private final Provider<TransactionRepository> transactionRepoProvider;

  public CalculateNetWorthUseCase_Factory(Provider<AccountRepository> accountRepoProvider,
      Provider<TransactionRepository> transactionRepoProvider) {
    this.accountRepoProvider = accountRepoProvider;
    this.transactionRepoProvider = transactionRepoProvider;
  }

  @Override
  public CalculateNetWorthUseCase get() {
    return newInstance(accountRepoProvider.get(), transactionRepoProvider.get());
  }

  public static CalculateNetWorthUseCase_Factory create(
      Provider<AccountRepository> accountRepoProvider,
      Provider<TransactionRepository> transactionRepoProvider) {
    return new CalculateNetWorthUseCase_Factory(accountRepoProvider, transactionRepoProvider);
  }

  public static CalculateNetWorthUseCase newInstance(AccountRepository accountRepo,
      TransactionRepository transactionRepo) {
    return new CalculateNetWorthUseCase(accountRepo, transactionRepo);
  }
}
