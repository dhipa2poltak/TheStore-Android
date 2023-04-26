package com.dpfht.thestore.feature_splash.di

import com.dpfht.thestore.feature_splash.SplashFragment
import com.dpfht.thestore.framework.di.NavigationComponent
import com.dpfht.thestore.framework.di.FragmentScope
import dagger.Component

@Component(dependencies = [NavigationComponent::class])
@FragmentScope
interface SplashComponent {

  fun inject(splashFragment: SplashFragment)
}