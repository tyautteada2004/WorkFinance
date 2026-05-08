package com.tyaut.workfinance.presentation.statistics;

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
public final class StatisticsViewModel_Factory implements Factory<StatisticsViewModel> {
  private final Provider<TransactionRepository> transactionRepoProvider;

  public StatisticsViewModel_Factory(Provider<TransactionRepository> transactionRepoProvider) {
    this.transactionRepoProvider = transactionRepoProvider;
  }

  @Override
  public StatisticsViewModel get() {
    return newInstance(transactionRepoProvider.get());
  }

  public static StatisticsViewModel_Factory create(
      Provider<TransactionRepository> transactionRepoProvider) {
    return new StatisticsViewModel_Factory(transactionRepoProvider);
  }

  public static StatisticsViewModel newInstance(TransactionRepository transactionRepo) {
    return new StatisticsViewModel(transactionRepo);
  }
}
