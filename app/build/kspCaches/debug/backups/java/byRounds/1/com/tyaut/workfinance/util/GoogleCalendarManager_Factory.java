package com.tyaut.workfinance.util;

import android.content.Context;
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
public final class GoogleCalendarManager_Factory implements Factory<GoogleCalendarManager> {
  private final Provider<Context> contextProvider;

  public GoogleCalendarManager_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public GoogleCalendarManager get() {
    return newInstance(contextProvider.get());
  }

  public static GoogleCalendarManager_Factory create(Provider<Context> contextProvider) {
    return new GoogleCalendarManager_Factory(contextProvider);
  }

  public static GoogleCalendarManager newInstance(Context context) {
    return new GoogleCalendarManager(context);
  }
}
