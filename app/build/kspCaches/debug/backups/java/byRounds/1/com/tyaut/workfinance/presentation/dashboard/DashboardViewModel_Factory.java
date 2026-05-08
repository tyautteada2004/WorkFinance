package com.tyaut.workfinance.presentation.dashboard;

import com.tyaut.workfinance.domain.usecase.CalculateNetWorthUseCase;
import com.tyaut.workfinance.domain.usecase.GetForecastTimelineUseCase;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<GetForecastTimelineUseCase> getForecastTimelineProvider;

  private final Provider<CalculateNetWorthUseCase> calculateNetWorthProvider;

  public DashboardViewModel_Factory(
      Provider<GetForecastTimelineUseCase> getForecastTimelineProvider,
      Provider<CalculateNetWorthUseCase> calculateNetWorthProvider) {
    this.getForecastTimelineProvider = getForecastTimelineProvider;
    this.calculateNetWorthProvider = calculateNetWorthProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(getForecastTimelineProvider.get(), calculateNetWorthProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<GetForecastTimelineUseCase> getForecastTimelineProvider,
      Provider<CalculateNetWorthUseCase> calculateNetWorthProvider) {
    return new DashboardViewModel_Factory(getForecastTimelineProvider, calculateNetWorthProvider);
  }

  public static DashboardViewModel newInstance(GetForecastTimelineUseCase getForecastTimeline,
      CalculateNetWorthUseCase calculateNetWorth) {
    return new DashboardViewModel(getForecastTimeline, calculateNetWorth);
  }
}
