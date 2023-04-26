package com.dpfht.thestore.feature_list.di

import com.dpfht.thestore.feature_list.view.ProductListFragment
import com.dpfht.thestore.framework.di.ApplicationComponent
import com.dpfht.thestore.framework.di.FragmentScope
import com.dpfht.thestore.framework.di.NavigationComponent
import dagger.Component

@Component(dependencies = [ApplicationComponent::class, NavigationComponent::class], modules = [ProductListModule::class])
@FragmentScope
interface ProductListComponent {

  fun inject(productListFragment: ProductListFragment)
}
