package com.tyaut.workfinance.presentation.workplace;

import com.tyaut.workfinance.data.repository.AccountRepository;
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
public final class WorkplaceViewModel_Factory implements Factory<WorkplaceViewModel> {
  private final Provider<WorkplaceRepository> workplaceRepoProvider;

  private final Provider<AccountRepository> accountRepoProvider;

  public WorkplaceViewModel_Factory(Provider<WorkplaceRepository> workplaceRepoProvider,
      Provider<AccountRepository> accountRepoProvider) {
    this.workplaceRepoProvider = workplaceRepoProvider;
    this.accountRepoProvider = accountRepoProvider;
  }

  @Override
  public WorkplaceViewModel get() {
    return newInstance(workplaceRepoProvider.get(), accountRepoProvider.get());
  }

  public static WorkplaceViewModel_Factory create(
      Provider<WorkplaceRepository> workplaceRepoProvider,
      Provider<AccountRepository> accountRepoProvider) {
    return new WorkplaceViewModel_Factory(workplaceRepoProvider, accountRepoProvider);
  }

  public static WorkplaceViewModel newInstance(WorkplaceRepository workplaceRepo,
      AccountRepository accountRepo) {
    return new WorkplaceViewModel(workplaceRepo, accountRepo);
  }
}
