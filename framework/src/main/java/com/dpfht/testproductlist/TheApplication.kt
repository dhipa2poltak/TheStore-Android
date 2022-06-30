package com.dpfht.testproductlist

import android.app.Application
import android.content.Context
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy.Builder
import android.os.StrictMode.VmPolicy
import androidx.multidex.MultiDex
import com.dpfht.testproductlist.framework.BuildConfig
import com.dpfht.testproductlist.framework.di.DaggerApplicationComponent

class TheApplication: Application() {

  companion object {
    lateinit var instance: TheApplication
  }

  lateinit var applicationComponent: com.dpfht.testproductlist.framework.di.ApplicationComponent

  override fun onCreate() {
    super.onCreate()
    instance = this

    if (BuildConfig.DEBUG) {
      StrictMode.setThreadPolicy(
        Builder().detectAll()
          .penaltyLog()
          .build()
      )
      StrictMode.setVmPolicy(
        VmPolicy.Builder().detectAll()
          .penaltyLog()
          .build()
      )
    }

    applicationComponent = DaggerApplicationComponent
      .builder()
      .applicationModule(com.dpfht.testproductlist.framework.di.module.ApplicationModule(this))
      .networkModule(com.dpfht.testproductlist.framework.di.module.NetworkModule())
      .build()
  }

  override fun attachBaseContext(base: Context) {
    super.attachBaseContext(base)
    MultiDex.install(this)
  }
}
