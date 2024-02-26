package com.dpfht.thestore.framework.di.module

import android.content.Context
import com.dpfht.thestore.data.datasource.AppDataSource
import com.dpfht.thestore.data.datasource.NetworkStateDataSource
import com.dpfht.thestore.data.repository.AppRepositoryImpl
import com.dpfht.thestore.domain.repository.AppRepository
import com.dpfht.thestore.framework.data.datasource.remote.rest.RestService
import com.dpfht.thestore.framework.data.datasource.local.LocalDataSourceImpl
import com.dpfht.thestore.framework.data.datasource.local.NetworkStateDataSourceImpl
import com.dpfht.thestore.framework.data.datasource.remote.RemoteDataSourceImpl
import com.dpfht.thestore.framework.di.ApplicationContext
import com.dpfht.thestore.framework.di.LocalDataSourceQ
import com.dpfht.thestore.framework.di.RemoteDataSourceQ
import com.dpfht.thestore.framework.data.datasource.local.onlinechecker.DefaultOnlineChecker
import com.dpfht.thestore.framework.data.datasource.local.onlinechecker.OnlineChecker
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {

  @Provides
  @ApplicationContext
  fun provideContext(): Context {
    return context
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
    return DefaultOnlineChecker(context)
  }

  @Provides
  @Singleton
  fun provideNetworkStateDataSource(onlineChecker: OnlineChecker): NetworkStateDataSource {
    return NetworkStateDataSourceImpl(onlineChecker)
  }

  @Provides
  @Singleton
  fun provideAppRepository(
    @RemoteDataSourceQ remoteDataSource: AppDataSource,
    @LocalDataSourceQ localDataSource: AppDataSource,
    networkStateDataSource: NetworkStateDataSource
  ): AppRepository {
    return AppRepositoryImpl(remoteDataSource, localDataSource, networkStateDataSource)
  }
}
