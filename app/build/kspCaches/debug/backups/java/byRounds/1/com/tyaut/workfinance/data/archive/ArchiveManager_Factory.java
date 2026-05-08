package com.tyaut.workfinance.data.archive;

import android.content.Context;
import com.tyaut.workfinance.data.repository.MonthlySummaryRepository;
import com.tyaut.workfinance.data.repository.ShiftRepository;
import com.tyaut.workfinance.data.repository.TransactionRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class ArchiveManager_Factory implements Factory<ArchiveManager> {
  private final Provider<Context> contextProvider;

  private final Provider<TransactionRepository> transactionRepoProvider;

  private final Provider<ShiftRepository> shiftRepoProvider;

  private final Provider<MonthlySummaryRepository> summaryRepoProvider;

  public ArchiveManager_Factory(Provider<Context> contextProvider,
      Provider<TransactionRepository> transactionRepoProvider,
      Provider<ShiftRepository> shiftRepoProvider,
      Provider<MonthlySummaryRepository> summaryRepoProvider) {
    this.contextProvider = contextProvider;
    this.transactionRepoProvider = transactionRepoProvider;
    this.shiftRepoProvider = shiftRepoProvider;
    this.summaryRepoProvider = summaryRepoProvider;
  }

  @Override
  public ArchiveManager get() {
    return newInstance(contextProvider.get(), transactionRepoProvider.get(), shiftRepoProvider.get(), summaryRepoProvider.get());
  }

  public static ArchiveManager_Factory create(Provider<Context> contextProvider,
      Provider<TransactionRepository> transactionRepoProvider,
      Provider<ShiftRepository> shiftRepoProvider,
      Provider<MonthlySummaryRepository> summaryRepoProvider) {
    return new ArchiveManager_Factory(contextProvider, transactionRepoProvider, shiftRepoProvider, summaryRepoProvider);
  }

  public static ArchiveManager newInstance(Context context, TransactionRepository transactionRepo,
      ShiftRepository shiftRepo, MonthlySummaryRepository summaryRepo) {
    return new ArchiveManager(context, transactionRepo, shiftRepo, summaryRepo);
  }
}
