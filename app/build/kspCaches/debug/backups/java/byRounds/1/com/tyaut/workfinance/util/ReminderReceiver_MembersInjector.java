package com.tyaut.workfinance.util;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class ReminderReceiver_MembersInjector implements MembersInjector<ReminderReceiver> {
  private final Provider<ReminderScheduler> schedulerProvider;

  public ReminderReceiver_MembersInjector(Provider<ReminderScheduler> schedulerProvider) {
    this.schedulerProvider = schedulerProvider;
  }

  public static MembersInjector<ReminderReceiver> create(
      Provider<ReminderScheduler> schedulerProvider) {
    return new ReminderReceiver_MembersInjector(schedulerProvider);
  }

  @Override
  public void injectMembers(ReminderReceiver instance) {
    injectScheduler(instance, schedulerProvider.get());
  }

  @InjectedFieldSignature("com.tyaut.workfinance.util.ReminderReceiver.scheduler")
  public static void injectScheduler(ReminderReceiver instance, ReminderScheduler scheduler) {
    instance.scheduler = scheduler;
  }
}
