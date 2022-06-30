package com.dpfht.testproductlist.feature_list.di

import com.dpfht.testproductlist.feature_list.view.ProductListFragment
import com.dpfht.testproductlist.framework.di.ApplicationComponent
import com.dpfht.testproductlist.framework.di.FragmentScope
import dagger.Component

@Component(dependencies = [ApplicationComponent::class], modules = [ProductListModule::class])
@FragmentScope
interface ProductListComponent {

  fun inject(productListFragment: ProductListFragment)
}
