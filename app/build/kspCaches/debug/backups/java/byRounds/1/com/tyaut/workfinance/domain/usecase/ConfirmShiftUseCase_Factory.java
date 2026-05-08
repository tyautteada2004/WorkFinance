package com.tyaut.workfinance.domain.usecase;

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
public final class ConfirmShiftUseCase_Factory implements Factory<ConfirmShiftUseCase> {
  private final Provider<ShiftRepository> shiftRepoProvider;

  private final Provider<TransactionRepository> transactionRepoProvider;

  private final Provider<WorkplaceRepository> workplaceRepoProvider;

  public ConfirmShiftUseCase_Factory(Provider<ShiftRepository> shiftRepoProvider,
      Provider<TransactionRepository> transactionRepoProvider,
      Provider<WorkplaceRepository> workplaceRepoProvider) {
    this.shiftRepoProvider = shiftRepoProvider;
    this.transactionRepoProvider = transactionRepoProvider;
    this.workplaceRepoProvider = workplaceRepoProvider;
  }

  @Override
  public ConfirmShiftUseCase get() {
    return newInstance(shiftRepoProvider.get(), transactionRepoProvider.get(), workplaceRepoProvider.get());
  }

  public static ConfirmShiftUseCase_Factory create(Provider<ShiftRepository> shiftRepoProvider,
      Provider<TransactionRepository> transactionRepoProvider,
      Provider<WorkplaceRepository> workplaceRepoProvider) {
    return new ConfirmShiftUseCase_Factory(shiftRepoProvider, transactionRepoProvider, workplaceRepoProvider);
  }

  public static ConfirmShiftUseCase newInstance(ShiftRepository shiftRepo,
      TransactionRepository transactionRepo, WorkplaceRepository workplaceRepo) {
    return new ConfirmShiftUseCase(shiftRepo, transactionRepo, workplaceRepo);
  }
}
