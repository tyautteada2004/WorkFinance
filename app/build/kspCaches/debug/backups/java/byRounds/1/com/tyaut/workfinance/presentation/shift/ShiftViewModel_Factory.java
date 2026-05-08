package com.tyaut.workfinance.presentation.shift;

import com.tyaut.workfinance.data.repository.ShiftRepository;
import com.tyaut.workfinance.data.repository.WorkplaceRepository;
import com.tyaut.workfinance.domain.usecase.CalculateShiftWageUseCase;
import com.tyaut.workfinance.domain.usecase.ConfirmShiftUseCase;
import com.tyaut.workfinance.util.GoogleCalendarManager;
import com.tyaut.workfinance.util.ReminderScheduler;
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
public final class ShiftViewModel_Factory implements Factory<ShiftViewModel> {
  private final Provider<ShiftRepository> shiftRepoProvider;

  private final Provider<WorkplaceRepository> workplaceRepoProvider;

  private final Provider<CalculateShiftWageUseCase> calculateWageProvider;

  private final Provider<ConfirmShiftUseCase> confirmShiftProvider;

  private final Provider<ReminderScheduler> reminderProvider;

  private final Provider<GoogleCalendarManager> calendarProvider;

  public ShiftViewModel_Factory(Provider<ShiftRepository> shiftRepoProvider,
      Provider<WorkplaceRepository> workplaceRepoProvider,
      Provider<CalculateShiftWageUseCase> calculateWageProvider,
      Provider<ConfirmShiftUseCase> confirmShiftProvider,
      Provider<ReminderScheduler> reminderProvider,
      Provider<GoogleCalendarManager> calendarProvider) {
    this.shiftRepoProvider = shiftRepoProvider;
    this.workplaceRepoProvider = workplaceRepoProvider;
    this.calculateWageProvider = calculateWageProvider;
    this.confirmShiftProvider = confirmShiftProvider;
    this.reminderProvider = reminderProvider;
    this.calendarProvider = calendarProvider;
  }

  @Override
  public ShiftViewModel get() {
    return newInstance(shiftRepoProvider.get(), workplaceRepoProvider.get(), calculateWageProvider.get(), confirmShiftProvider.get(), reminderProvider.get(), calendarProvider.get());
  }

  public static ShiftViewModel_Factory create(Provider<ShiftRepository> shiftRepoProvider,
      Provider<WorkplaceRepository> workplaceRepoProvider,
      Provider<CalculateShiftWageUseCase> calculateWageProvider,
      Provider<ConfirmShiftUseCase> confirmShiftProvider,
      Provider<ReminderScheduler> reminderProvider,
      Provider<GoogleCalendarManager> calendarProvider) {
    return new ShiftViewModel_Factory(shiftRepoProvider, workplaceRepoProvider, calculateWageProvider, confirmShiftProvider, reminderProvider, calendarProvider);
  }

  public static ShiftViewModel newInstance(ShiftRepository shiftRepo,
      WorkplaceRepository workplaceRepo, CalculateShiftWageUseCase calculateWage,
      ConfirmShiftUseCase confirmShift, ReminderScheduler reminder,
      GoogleCalendarManager calendar) {
    return new ShiftViewModel(shiftRepo, workplaceRepo, calculateWage, confirmShift, reminder, calendar);
  }
}
