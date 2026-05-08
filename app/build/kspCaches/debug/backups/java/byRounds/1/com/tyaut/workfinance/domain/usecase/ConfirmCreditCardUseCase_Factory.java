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
public final class ConfirmCreditCardUseCase_Factory implements Factory<ConfirmCreditCardUseCase> {
  private final Provider<TransactionRepository> transactionRepoProvider;

  private final Provider<AccountRepository> accountRepoProvider;

  public ConfirmCreditCardUseCase_Factory(Provider<TransactionRepository> transactionRepoProvider,
      Provider<AccountRepository> accountRepoProvider) {
    this.transactionRepoProvider = transactionRepoProvider;
    this.accountRepoProvider = accountRepoProvider;
  }

  @Override
  public ConfirmCreditCardUseCase get() {
    return newInstance(transactionRepoProvider.get(), accountRepoProvider.get());
  }

  public static ConfirmCreditCardUseCase_Factory create(
      Provider<TransactionRepository> transactionRepoProvider,
      Provider<AccountRepository> accountRepoProvider) {
    return new ConfirmCreditCardUseCase_Factory(transactionRepoProvider, accountRepoProvider);
  }

  public static ConfirmCreditCardUseCase newInstance(TransactionRepository transactionRepo,
      AccountRepository accountRepo) {
    return new ConfirmCreditCardUseCase(transactionRepo, accountRepo);
  }
}
