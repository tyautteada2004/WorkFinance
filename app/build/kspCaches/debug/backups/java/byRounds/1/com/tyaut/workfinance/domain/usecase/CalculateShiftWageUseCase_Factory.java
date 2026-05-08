package com.tyaut.workfinance.domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class CalculateShiftWageUseCase_Factory implements Factory<CalculateShiftWageUseCase> {
  @Override
  public CalculateShiftWageUseCase get() {
    return newInstance();
  }

  public static CalculateShiftWageUseCase_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CalculateShiftWageUseCase newInstance() {
    return new CalculateShiftWageUseCase();
  }

  private static final class InstanceHolder {
    private static final CalculateShiftWageUseCase_Factory INSTANCE = new CalculateShiftWageUseCase_Factory();
  }
}
