package com.dpfht.thestore.framework.di

import com.dpfht.thestore.domain.repository.AppRepository
import com.dpfht.thestore.framework.di.module.ApplicationModule
import com.dpfht.thestore.framework.di.module.NetworkModule
import com.dpfht.thestore.framework.data.datasource.local.onlinechecker.OnlineChecker
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent {

  fun getAppRepository(): AppRepository
  fun getOnlineChecker(): OnlineChecker
}
