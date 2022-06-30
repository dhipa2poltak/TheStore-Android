package com.dpfht.testproductlist.feature_list.di

import android.content.Context
import com.dpfht.testproductlist.data.model.remote.Product
import com.dpfht.testproductlist.data.repository.AppRepository
import com.dpfht.testproductlist.domain.usecase.GetProductsUseCase
import com.dpfht.testproductlist.domain.usecase.GetProductsUseCaseImpl
import com.dpfht.testproductlist.feature_list.adapter.ProductListAdapter
import com.dpfht.testproductlist.feature_list.view.ProductListFragment
import com.dpfht.testproductlist.feature_list.view.ProductListViewModel
import com.dpfht.testproductlist.framework.di.ActivityContext
import com.dpfht.testproductlist.framework.di.FragmentScope
import com.dpfht.testproductlist.util.net.OnlineChecker
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [FragmentModule::class])
class ProductListModule(private val productListFragment: ProductListFragment) {

  @Provides
  @ActivityContext
  @FragmentScope
  fun getContext(): Context {
    return productListFragment.requireActivity()
  }

  @Provides
  @FragmentScope
  fun provideCompositeDisposable(): CompositeDisposable {
    return CompositeDisposable()
  }

  @Provides
  @FragmentScope
  fun provideProducts(): ArrayList<Product> {
    return arrayListOf()
  }

  @Provides
  @FragmentScope
  fun provideGetProductsUseCase(appRepository: AppRepository): GetProductsUseCase {
    return GetProductsUseCaseImpl(appRepository)
  }

  @Provides
  @FragmentScope
  fun provideProductListViewModel(
    getProductsUseCase: GetProductsUseCase,
    compositeDisposable: CompositeDisposable,
    products: ArrayList<Product>,
    onlineChecker: OnlineChecker
  ): ProductListViewModel {
    return ProductListViewModel(getProductsUseCase, compositeDisposable, products, onlineChecker)
  }

  @Provides
  @FragmentScope
  fun provideProductListAdapter(products: ArrayList<Product>): ProductListAdapter {
    return ProductListAdapter(products)
  }
}
