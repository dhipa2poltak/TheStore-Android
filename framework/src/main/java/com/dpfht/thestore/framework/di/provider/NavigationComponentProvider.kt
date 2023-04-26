package com.dpfht.thestore.framework.di.provider

import com.dpfht.thestore.framework.di.NavigationComponent

interface NavigationComponentProvider {

  fun provideNavigationComponent(): NavigationComponent
}
