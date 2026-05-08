package com.tyaut.workfinance.presentation.shift;

import com.tyaut.workfinance.domain.usecase.CalculateShiftWageUseCase;
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
public final class ActualShiftInputBottomSheet_MembersInjector implements MembersInjector<ActualShiftInputBottomSheet> {
  private final Provider<CalculateShiftWageUseCase> calculateWageProvider;

  public ActualShiftInputBottomSheet_MembersInjector(
      Provider<CalculateShiftWageUseCase> calculateWageProvider) {
    this.calculateWageProvider = calculateWageProvider;
  }

  public static MembersInjector<ActualShiftInputBottomSheet> create(
      Provider<CalculateShiftWageUseCase> calculateWageProvider) {
    return new ActualShiftInputBottomSheet_MembersInjector(calculateWageProvider);
  }

  @Override
  public void injectMembers(ActualShiftInputBottomSheet instance) {
    injectCalculateWage(instance, calculateWageProvider.get());
  }

  @InjectedFieldSignature("com.tyaut.workfinance.presentation.shift.ActualShiftInputBottomSheet.calculateWage")
  public static void injectCalculateWage(ActualShiftInputBottomSheet instance,
      CalculateShiftWageUseCase calculateWage) {
    instance.calculateWage = calculateWage;
  }
}
