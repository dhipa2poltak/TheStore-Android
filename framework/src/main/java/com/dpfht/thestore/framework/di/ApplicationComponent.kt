package com.dpfht.thestore.framework.di

import com.dpfht.thestore.data.repository.AppRepository
import com.dpfht.thestore.framework.di.module.ApplicationModule
import com.dpfht.thestore.framework.di.module.NetworkModule
import com.dpfht.thestore.util.net.OnlineChecker
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent {

  fun getAppRepository(): AppRepository
  fun getOnlineChecker(): OnlineChecker
}
