package com.dpfht.thestore.framework.di

import com.dpfht.thestore.framework.di.module.NavigationModule
import com.dpfht.thestore.framework.navigation.NavigationService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NavigationModule::class])
interface NavigationComponent {

  fun getNavigationService(): NavigationService
}
