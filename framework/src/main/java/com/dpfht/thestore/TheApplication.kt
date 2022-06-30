package com.dpfht.thestore

import android.app.Application
import android.content.Context
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy.Builder
import android.os.StrictMode.VmPolicy
import androidx.multidex.MultiDex
import com.dpfht.thestore.framework.BuildConfig
import com.dpfht.thestore.framework.di.ApplicationComponent
import com.dpfht.thestore.framework.di.DaggerApplicationComponent
import com.dpfht.thestore.framework.di.module.ApplicationModule
import com.dpfht.thestore.framework.di.module.NetworkModule

class TheApplication: Application() {

  companion object {
    lateinit var instance: TheApplication
  }

  lateinit var applicationComponent: ApplicationComponent

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
      .applicationModule(ApplicationModule(this))
      .networkModule(NetworkModule())
      .build()
  }

  override fun attachBaseContext(base: Context) {
    super.attachBaseContext(base)
    MultiDex.install(this)
  }
}
