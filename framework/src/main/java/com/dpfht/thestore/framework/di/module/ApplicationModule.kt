package com.dpfht.thestore.framework.di.module

import android.content.Context
import com.dpfht.thestore.TheApplication
import com.dpfht.thestore.data.repository.AppDataSource
import com.dpfht.thestore.data.repository.AppRepository
import com.dpfht.thestore.framework.rest.api.AppRepositoryImpl
import com.dpfht.thestore.framework.di.ApplicationContext
import com.dpfht.thestore.framework.di.LocalDataSourceQ
import com.dpfht.thestore.framework.di.RemoteDataSourceQ
import com.dpfht.thestore.framework.rest.api.LocalDataSourceImpl
import com.dpfht.thestore.framework.rest.api.RemoteDataSourceImpl
import com.dpfht.thestore.framework.rest.api.RestService
import com.dpfht.thestore.util.net.DefaultOnlineChecker
import com.dpfht.thestore.util.net.OnlineChecker
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val theApplication: TheApplication) {

  @Provides
  @ApplicationContext
  fun provideContext(): Context {
    return theApplication
  }

  @Provides
  @Singleton
  @RemoteDataSourceQ
  fun provideRemoteDataSource(restService: RestService): AppDataSource {
    return RemoteDataSourceImpl(restService)
  }

  @Provides
  @Singleton
  @LocalDataSourceQ
  fun provideLocalDataSource(@ApplicationContext context: Context): AppDataSource {
    return LocalDataSourceImpl(context.assets)
  }

  @Provides
  @Singleton
  fun provideOnlineChecker(): OnlineChecker {
    return DefaultOnlineChecker(theApplication)
  }

  @Provides
  @Singleton
  fun provideAppRepository(
    @RemoteDataSourceQ remoteDataSource: AppDataSource,
    @LocalDataSourceQ localDataSource: AppDataSource,
    onlineChecker: OnlineChecker
  ): AppRepository {
    return AppRepositoryImpl(remoteDataSource, localDataSource, onlineChecker)
  }
}
