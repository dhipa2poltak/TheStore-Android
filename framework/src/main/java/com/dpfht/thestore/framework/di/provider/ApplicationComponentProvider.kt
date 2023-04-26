package com.dpfht.thestore.framework.di.provider

import com.dpfht.thestore.framework.di.ApplicationComponent

interface ApplicationComponentProvider {

  fun provideApplicationComponent(): ApplicationComponent
}
