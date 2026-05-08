package com.tyaut.workfinance.data.repository;

import com.tyaut.workfinance.data.db.dao.WorkplaceDao;
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
public final class WorkplaceRepositoryImpl_Factory implements Factory<WorkplaceRepositoryImpl> {
  private final Provider<WorkplaceDao> daoProvider;

  public WorkplaceRepositoryImpl_Factory(Provider<WorkplaceDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public WorkplaceRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static WorkplaceRepositoryImpl_Factory create(Provider<WorkplaceDao> daoProvider) {
    return new WorkplaceRepositoryImpl_Factory(daoProvider);
  }

  public static WorkplaceRepositoryImpl newInstance(WorkplaceDao dao) {
    return new WorkplaceRepositoryImpl(dao);
  }
}
