package com.dpfht.testproductlist.framework.di.module

import android.content.Context
import com.dpfht.testproductlist.TheApplication
import com.dpfht.testproductlist.data.repository.AppDataSource
import com.dpfht.testproductlist.data.repository.AppRepository
import com.dpfht.testproductlist.framework.rest.api.AppRepositoryImpl
import com.dpfht.testproductlist.framework.di.ApplicationContext
import com.dpfht.testproductlist.framework.di.LocalDataSourceQ
import com.dpfht.testproductlist.framework.di.RemoteDataSourceQ
import com.dpfht.testproductlist.framework.rest.api.LocalDataSourceImpl
import com.dpfht.testproductlist.framework.rest.api.RemoteDataSourceImpl
import com.dpfht.testproductlist.framework.rest.api.RestService
import com.dpfht.testproductlist.util.net.DefaultOnlineChecker
import com.dpfht.testproductlist.util.net.OnlineChecker
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
