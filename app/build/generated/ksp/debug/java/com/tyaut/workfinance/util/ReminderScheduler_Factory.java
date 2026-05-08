package com.tyaut.workfinance.util;

import android.content.Context;
import com.tyaut.workfinance.data.repository.ShiftRepository;
import com.tyaut.workfinance.data.repository.WorkplaceRepository;
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
public final class ReminderScheduler_Factory implements Factory<ReminderScheduler> {
  private final Provider<Context> contextProvider;

  private final Provider<ShiftRepository> shiftRepoProvider;

  private final Provider<WorkplaceRepository> workplaceRepoProvider;

  public ReminderScheduler_Factory(Provider<Context> contextProvider,
      Provider<ShiftRepository> shiftRepoProvider,
      Provider<WorkplaceRepository> workplaceRepoProvider) {
    this.contextProvider = contextProvider;
    this.shiftRepoProvider = shiftRepoProvider;
    this.workplaceRepoProvider = workplaceRepoProvider;
  }

  @Override
  public ReminderScheduler get() {
    return newInstance(contextProvider.get(), shiftRepoProvider.get(), workplaceRepoProvider.get());
  }

  public static ReminderScheduler_Factory create(Provider<Context> contextProvider,
      Provider<ShiftRepository> shiftRepoProvider,
      Provider<WorkplaceRepository> workplaceRepoProvider) {
    return new ReminderScheduler_Factory(contextProvider, shiftRepoProvider, workplaceRepoProvider);
  }

  public static ReminderScheduler newInstance(Context context, ShiftRepository shiftRepo,
      WorkplaceRepository workplaceRepo) {
    return new ReminderScheduler(context, shiftRepo, workplaceRepo);
  }
}
