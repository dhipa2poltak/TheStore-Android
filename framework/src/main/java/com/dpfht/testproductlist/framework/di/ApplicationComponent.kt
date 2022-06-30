package com.dpfht.testproductlist.framework.di

import com.dpfht.testproductlist.data.repository.AppRepository
import com.dpfht.testproductlist.framework.di.module.ApplicationModule
import com.dpfht.testproductlist.framework.di.module.NetworkModule
import com.dpfht.testproductlist.util.net.OnlineChecker
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent {

  fun getAppRepository(): AppRepository
  fun getOnlineChecker(): OnlineChecker
}
