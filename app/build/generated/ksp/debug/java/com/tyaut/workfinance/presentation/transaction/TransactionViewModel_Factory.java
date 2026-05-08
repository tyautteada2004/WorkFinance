package com.tyaut.workfinance.presentation.transaction;

import com.tyaut.workfinance.data.repository.AccountRepository;
import com.tyaut.workfinance.data.repository.TransactionRepository;
import com.tyaut.workfinance.domain.usecase.ConfirmCreditCardUseCase;
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
public final class TransactionViewModel_Factory implements Factory<TransactionViewModel> {
  private final Provider<TransactionRepository> transactionRepoProvider;

  private final Provider<AccountRepository> accountRepoProvider;

  private final Provider<ConfirmCreditCardUseCase> confirmCreditCardProvider;

  public TransactionViewModel_Factory(Provider<TransactionRepository> transactionRepoProvider,
      Provider<AccountRepository> accountRepoProvider,
      Provider<ConfirmCreditCardUseCase> confirmCreditCardProvider) {
    this.transactionRepoProvider = transactionRepoProvider;
    this.accountRepoProvider = accountRepoProvider;
    this.confirmCreditCardProvider = confirmCreditCardProvider;
  }

  @Override
  public TransactionViewModel get() {
    return newInstance(transactionRepoProvider.get(), accountRepoProvider.get(), confirmCreditCardProvider.get());
  }

  public static TransactionViewModel_Factory create(
      Provider<TransactionRepository> transactionRepoProvider,
      Provider<AccountRepository> accountRepoProvider,
      Provider<ConfirmCreditCardUseCase> confirmCreditCardProvider) {
    return new TransactionViewModel_Factory(transactionRepoProvider, accountRepoProvider, confirmCreditCardProvider);
  }

  public static TransactionViewModel newInstance(TransactionRepository transactionRepo,
      AccountRepository accountRepo, ConfirmCreditCardUseCase confirmCreditCard) {
    return new TransactionViewModel(transactionRepo, accountRepo, confirmCreditCard);
  }
}
