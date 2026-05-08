package com.tyaut.workfinance.domain.usecase;

import com.tyaut.workfinance.data.repository.AccountRepository;
import com.tyaut.workfinance.data.repository.FixedExpenseRepository;
import com.tyaut.workfinance.data.repository.ShiftRepository;
import com.tyaut.workfinance.data.repository.TransactionRepository;
import com.tyaut.workfinance.data.repository.WorkplaceRepository;
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
public final class GetForecastTimelineUseCase_Factory implements Factory<GetForecastTimelineUseCase> {
  private final Provider<AccountRepository> accountRepoProvider;

  private final Provider<TransactionRepository> transactionRepoProvider;

  private final Provider<FixedExpenseRepository> fixedExpenseRepoProvider;

  private final Provider<ShiftRepository> shiftRepoProvider;

  private final Provider<WorkplaceRepository> workplaceRepoProvider;

  public GetForecastTimelineUseCase_Factory(Provider<AccountRepository> accountRepoProvider,
      Provider<TransactionRepository> transactionRepoProvider,
      Provider<FixedExpenseRepository> fixedExpenseRepoProvider,
      Provider<ShiftRepository> shiftRepoProvider,
      Provider<WorkplaceRepository> workplaceRepoProvider) {
    this.accountRepoProvider = accountRepoProvider;
    this.transactionRepoProvider = transactionRepoProvider;
    this.fixedExpenseRepoProvider = fixedExpenseRepoProvider;
    this.shiftRepoProvider = shiftRepoProvider;
    this.workplaceRepoProvider = workplaceRepoProvider;
  }

  @Override
  public GetForecastTimelineUseCase get() {
    return newInstance(accountRepoProvider.get(), transactionRepoProvider.get(), fixedExpenseRepoProvider.get(), shiftRepoProvider.get(), workplaceRepoProvider.get());
  }

  public static GetForecastTimelineUseCase_Factory create(
      Provider<AccountRepository> accountRepoProvider,
      Provider<TransactionRepository> transactionRepoProvider,
      Provider<FixedExpenseRepository> fixedExpenseRepoProvider,
      Provider<ShiftRepository> shiftRepoProvider,
      Provider<WorkplaceRepository> workplaceRepoProvider) {
    return new GetForecastTimelineUseCase_Factory(accountRepoProvider, transactionRepoProvider, fixedExpenseRepoProvider, shiftRepoProvider, workplaceRepoProvider);
  }

  public static GetForecastTimelineUseCase newInstance(AccountRepository accountRepo,
      TransactionRepository transactionRepo, FixedExpenseRepository fixedExpenseRepo,
      ShiftRepository shiftRepo, WorkplaceRepository workplaceRepo) {
    return new GetForecastTimelineUseCase(accountRepo, transactionRepo, fixedExpenseRepo, shiftRepo, workplaceRepo);
  }
}
