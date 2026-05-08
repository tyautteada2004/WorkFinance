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
public final class ShiftInputBottomSheet_MembersInjector implements MembersInjector<ShiftInputBottomSheet> {
  private final Provider<CalculateShiftWageUseCase> calculateWageProvider;

  public ShiftInputBottomSheet_MembersInjector(
      Provider<CalculateShiftWageUseCase> calculateWageProvider) {
    this.calculateWageProvider = calculateWageProvider;
  }

  public static MembersInjector<ShiftInputBottomSheet> create(
      Provider<CalculateShiftWageUseCase> calculateWageProvider) {
    return new ShiftInputBottomSheet_MembersInjector(calculateWageProvider);
  }

  @Override
  public void injectMembers(ShiftInputBottomSheet instance) {
    injectCalculateWage(instance, calculateWageProvider.get());
  }

  @InjectedFieldSignature("com.tyaut.workfinance.presentation.shift.ShiftInputBottomSheet.calculateWage")
  public static void injectCalculateWage(ShiftInputBottomSheet instance,
      CalculateShiftWageUseCase calculateWage) {
    instance.calculateWage = calculateWage;
  }
}
